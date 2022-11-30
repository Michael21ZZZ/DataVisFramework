package edu.cmu.cs214.hw6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.xml.crypto.Data;

import org.json.JSONObject;
import org.json.JSONArray;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFrameworkImpl;
import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private WorkFlowFrameworkImpl workFlow;
    private List<DataPlugin> dataPlugins;
    
    private JSONObject processedData;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        this.workFlow = new WorkFlowFrameworkImpl();
        dataPlugins = loadPlugins();
        for (DataPlugin p: dataPlugins){
            workFlow.registerPlugin(p);
        }

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        JSONObject responseJson = new JSONObject();
        if (uri.equals("/dataplugin")) {
            // e.g., /dataplugin?i=0
            DataPlugin dataPlugin = dataPlugins.get(Integer.parseInt(params.get("i")));
            workFlow.registerPlugin(dataPlugin);
            String name = dataPlugin.getPluginName();
            String instr = dataPlugin.getPluginInstructions();
            JSONObject dataPluginInfo = new JSONObject();
            dataPluginInfo.put("name", name);
            dataPluginInfo.put("instruction", instr);
            return newFixedLengthResponse(responseJson.toString());
        } else if (uri.equals("/submitdata")){
            // e.g., /submitdata?keyword=XX&tabularinput=XX
            try {
                UnProcessedData UnProcessedData = workFlow.fetchData(parseParams(params)); // TODO!
                this.processedData = workFlow.processData(UnProcessedData);
                responseJson.put("datasubmitsuccess", true);
                System.out.println(responseJson);
                return newFixedLengthResponse(responseJson.toString());
            } catch(Exception e) {
                responseJson.put("datasubmitsuccess", false);
                return newFixedLengthResponse(responseJson.toString());
            }
        } else if (uri.equals("visplugin")) {
            
        }
        return newFixedLengthResponse("");
    }


    /**
     * Load plugins listed in META-INF/services/...
     *
     * @return List of instantiated plugins
     */
    private static List<DataPlugin> loadPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }

    public static class Test {
        public String getText() {
            return "Hello World!";
        }
    }
    
    /**
     * Parse params into a search item before feeding into fetchData. 
     * @param params
     * @return searchTerm 
     */
    private SearchTerm parseParams(Map<String, String> params) {
        String keyword = params.get("keyword");
        // JSONArray tabularInput = new JSONArray(params.get("tabularInput")); // TODO!
        SearchTerm searchTerm = new SearchTerm(keyword);

        return searchTerm;
    }
}
