package edu.cmu.cs214.hw6.framework.core;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.cs214.hw6.NLP.NLPHelper;

public class WorkFlowFrameworkImpl implements WorkFlowFramework{
    private DataPlugin currentPlugin;
    private List<DataPlugin>  registeredPlugins;

    public WorkFlowFrameworkImpl() {
        this.registeredPlugins = new ArrayList<DataPlugin>();
    }

    /**
     * Registers a new {@link DataPlugin} with the game framework
     */
    public void registerPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredPlugins.add(plugin);
    }

    /**
     * Set current {@link DataPlugin} 
     */
    public void setCurrentPlugin(DataPlugin plugin) {
        this.currentPlugin = plugin;
    }

    public UnProcessedData fetchData(String keywords) {
        if (this.currentPlugin != null) {
            this.currentPlugin.search(keywords);
            return this.currentPlugin.getData();
        } else {
            return null;
        }
    }

    public JSONObject processData(UnProcessedData unprocessedData) {
        boolean isTabular = unprocessedData.isTabular();
        NLPHelper nlpHelper = new NLPHelper();
        if (!isTabular) { // if it's pure text, needs to partitioned
            return new JSONObject();
        } else {
            JSONArray tabularData = unprocessedData.tabularData();
            boolean hasTime = unprocessedData.hasTime();
            boolean hasLocation = unprocessedData.hasLocation();
            if (!hasTime) {
                tabularData = nlpHelper.parseTime(tabularData);
            }
            if (!hasLocation) {
                tabularData = nlpHelper.parseLocation(tabularData);
            }
            JSONObject res = new JSONObject();
            res.put("CoreData", tabularData);
            res.put("LocationFreq", new JSONObject()); // TODO
            return res;
        }
    }
}
