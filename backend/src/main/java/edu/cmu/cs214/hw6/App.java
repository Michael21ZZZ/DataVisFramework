package edu.cmu.cs214.hw6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.xml.crypto.Data;

import org.json.JSONObject;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
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
    private List<DataPlugin> plugins;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        this.workFlow = new WorkFlowFrameworkImpl();
        plugins = loadPlugins();
        for (DataPlugin p: plugins){
            workFlow.registerPlugin(p);
        }
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/plugin")) {
            // e.g., /plugin?i=0
            workFlow.registerPlugin(plugins.get(Integer.parseInt(params.get("i"))));
            return newFixedLengthResponse("");
        } else if (uri.equals("/submitdata")){
            // e.g., /play?x=1&y=1
            UnProcessedData UnProcessedData = workFlow.fetchData(params.get("keyword")); // TODO
            JSONObject processedData = workFlow.processData(UnProcessedData);
            return newFixedLengthResponse(processedData.toString());
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
            System.out.println("Loaded plugin " + plugin.getData());
            result.add(plugin);
        }
        return result;
    }

    public static class Test {
        public String getText() {
            return "Hello World!";
        }
    }
}
