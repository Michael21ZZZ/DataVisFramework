package edu.cmu.cs214.hw6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.json.JSONObject;
import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.ProcessedData;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.VisPlugin;
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
    private ProcessedData processedData;
    private JSONObject responseJson = new JSONObject();

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        this.workFlow = new WorkFlowFrameworkImpl();
        this.dataPlugins = loadDataPlugins();
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
        if (uri.equals("/dataplugin")) {
            // e.g., /dataplugin?i=0
            DataPlugin dataPlugin = dataPlugins.get(Integer.parseInt(params.get("i")));
            workFlow.setCurrentDataPlugin(dataPlugin);
            String name = dataPlugin.getPluginName();
            String instr = dataPlugin.getPluginInstructions();
            this.responseJson = new JSONObject(); // clear responseJson
            responseJson.put("name", name);
            responseJson.put("instruction", instr);
            System.out.println(responseJson.toString());
        } else if (uri.equals("/submitdata")){
            // e.g., /submitdata?keyword=XX&tabularinput=XX
            try {
                UnProcessedData UnProcessedData = workFlow.fetchData(parseParams(params));
                System.out.println(UnProcessedData.textData());
                this.processedData = workFlow.processData(UnProcessedData);
                this.responseJson.put("processedata", this.processedData);
                responseJson.put("datasubmitsuccess", true);
            } catch(Exception e) {
                System.out.println(e);
                responseJson.put("datasubmitsuccess", false);
            }
        } else if (uri.equals("/visplugin")) {
            VisPlugin visPlugin = visPlugins.get(Integer.parseInt(params.get("i")));
            this.workFlow.setCurrentVisPlugin(visPlugin);
            return newFixedLengthResponse(workFlow.prepVis(this.processedData).toString());
        } else if (uri.equals("/register")) {
            ArrayList<String> dataplugins = new ArrayList<String>();
            ArrayList<String> visplugins = new ArrayList<String>();
            for (int i = 0; i < this.dataPlugins.size(); i++) {
                dataplugins.add(this.dataPlugins.get(i).getPluginName());
            }
            for (int i = 0; i < this.visPlugins.size(); i++) {
                visplugins.add(this.visPlugins.get(i).getPluginName());
            }
            responseJson.put("dataplugins", dataplugins);
            responseJson.put("visplugins", visplugins);
            return newFixedLengthResponse(responseJson.toString());
        }
        return newFixedLengthResponse(responseJson.toString());
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
        // JSONArray tabularInput = new JSONArray(params.get("tabularInput"));
        SearchTerm searchTerm = new SearchTerm(keyword);

        return searchTerm;
    }

}

