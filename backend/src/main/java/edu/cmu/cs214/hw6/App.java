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
import edu.cmu.cs214.hw6.framework.core.VisPlugin;
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
    private List<VisPlugin> visPlugins;
    private JSONObject processedData;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        this.workFlow = new WorkFlowFrameworkImpl();
        dataPlugins = loadDataPlugins();
        this.visPlugins = loadVisPlugins();
        for (DataPlugin p: dataPlugins){
            workFlow.registerDataPlugin(p);
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
            workFlow.setCurrentDataPlugin(dataPlugin);
            String name = dataPlugin.getPluginName();
            String instr = dataPlugin.getPluginInstructions();
            responseJson.put("name", name);
            responseJson.put("instruction", instr);
            System.out.println(responseJson.toString());
            return newFixedLengthResponse(responseJson.toString());
        } else if (uri.equals("/submitdata")){
            // e.g., /submitdata?keyword=XX&tabularinput=XX
            try {
                UnProcessedData UnProcessedData = workFlow.fetchData(parseParams(params)); // TODO!
                System.out.println(UnProcessedData.textData());
                this.processedData = workFlow.processData(UnProcessedData);
                responseJson.put("datasubmitsuccess", true);
                return newFixedLengthResponse(responseJson.toString());
            } catch(Exception e) {
                System.out.println(e);
                responseJson.put("datasubmitsuccess", false);
                return newFixedLengthResponse(responseJson.toString());
            }
        } else if (uri.equals("/visplugin")) {
            VisPlugin visPlugin = visPlugins.get(Integer.parseInt(params.get("i")));
            this.workFlow.setCurrentVisPlugin(visPlugin);
            return newFixedLengthResponse(workFlow.prepVis(this.processedData).toString());
        } else if (uri.equals("/register")) {
            String[] dataplugins = {};
            String[] visplugins = {};
            for (int i = 0; i < this.dataPlugins.size(); i++) {
                dataplugins[i] = this.dataPlugins.get(i).getPluginName();
            }
            for (int i = 0; i < this.visPlugins.size(); i++) {
                visplugins[i] = this.visPlugins.get(i).getPluginName();
            }
            responseJson.put("dataplugins", dataplugins);
            responseJson.put("visplugins", visplugins);
            return newFixedLengthResponse(responseJson.toString());
        }
        return newFixedLengthResponse("Hello World");
    }


    /**
     * Load plugins listed in META-INF/services/...
     *
     * @return List of instantiated plugins
     */
    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin plugin : plugins) {
            System.out.println("Loaded Data plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }

    /**
     * Load plugins listed in META-INF/services/...
     *
     * @return List of instantiated plugins
     */
    private static List<VisPlugin> loadVisPlugins() {
        ServiceLoader<VisPlugin> plugins = ServiceLoader.load(VisPlugin.class);
        List<VisPlugin> result = new ArrayList<>();
        for (VisPlugin plugin : plugins) {
            System.out.println("Loaded Geo plugin " + plugin.getPluginName());
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

