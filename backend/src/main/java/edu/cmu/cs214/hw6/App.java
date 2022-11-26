package edu.cmu.cs214.hw6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.xml.crypto.Data;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
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
        } else if (uri.equals("/play")){
            // e.g., /play?x=1&y=1
            if (game.hasGame()) {
                game.playMove(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
            }
        } else if (uri.equals("/start")){

        }
        Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.toString());
        return newFixedLengthResponse("Hello World!");
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
