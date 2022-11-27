package edu.cmu.cs214.hw6.framework.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.cs214.hw6.NLP.GoogleGeoCoding;
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

    public UnProcessedData fetchData(SearchTerm searchTerm) {
        if (this.currentPlugin != null) {
            this.currentPlugin.search(searchTerm);
            return this.currentPlugin.getData();
        } else {
            return null;
        }
    }

    /**
     * Process data from plugin, nlp extract time/location, and calculate location freq
     * @param unprocessedData
     * @return
     */
    public JSONObject processData(UnProcessedData unprocessedData) {
        boolean isTabular = unprocessedData.isTabular();
        NLPHelper nlpHelper = new NLPHelper();
        if (!isTabular) { // if it's pure text, needs to partitioned
            return new JSONObject();
        } else {
            JSONArray tabularData = unprocessedData.tabularData();
            if (!unprocessedData.hasTime()) {
                tabularData = nlpHelper.parseTime(tabularData);
            }
            if (!unprocessedData.hasLocation()) {
                tabularData = nlpHelper.parseLocation(tabularData);
            }
            // A map store the freq of a location
            Map<String, Integer> locFreqMap = new HashMap<String, Integer>();
            // iterate through the data rows; add location; update location freq map
            GoogleGeoCoding ggc = new GoogleGeoCoding();
            for (int i = 0; i < tabularData.length(); i++) {
                JSONObject row = tabularData.getJSONObject(i);
                String location = row.getString("location");
                JSONObject coord = ggc.getCord(location);
                row.put("lng", coord.getDouble("lng"));
                row.put("lat", coord.getDouble("lat"));
                locFreqMap.put(location, locFreqMap.getOrDefault(location, 0) + 1);
            }
            JSONObject res = new JSONObject();
            res.put("coreData", tabularData);
            res.put("locationFreq", locFreqMap);
            return res;
        }
    }
}
