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
    private DataPlugin currentDataPlugin;
    private List<DataPlugin>  registeredDataPlugins;
    private VisPlugin currentVisPlugin;
    private List<VisPlugin>  registeredVisPlugins;
    private NLPHelper nlpHelper;

    public WorkFlowFrameworkImpl() {
        this.registeredDataPlugins = new ArrayList<DataPlugin>();
        this.registeredVisPlugins = new ArrayList<VisPlugin>();
        this.nlpHelper = new NLPHelper();
    }

    /**
     * Registers a new {@link DataPlugin} with the game framework
     */
    public void registerDataPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredDataPlugins.add(plugin);
    }

    /**
     * Set current {@link DataPlugin} 
     */
    public void setCurrentDataPlugin(DataPlugin plugin) {
        this.currentDataPlugin = plugin;
    }

    /**
     * Get data using data plugin
     * @param searchTerm
     * @return
     */
    public UnProcessedData fetchData(SearchTerm searchTerm) {
        if (this.currentDataPlugin != null) {
            this.currentDataPlugin.search(searchTerm);
            return this.currentDataPlugin.getData();
        } else {
            return null;
        }
    }

    /**
     * Registers a new {@link VisPlugin} with the framework
     */
    public void registerVisPlugin(VisPlugin plugin) {
        plugin.onRegister(this);
        registeredVisPlugins.add(plugin);
    }

    /**
     * Set current {@link VisPlugin} 
     */
    public void setCurrentVisPlugin(VisPlugin plugin) {
        this.currentVisPlugin = plugin;
    }

    /**
     * Prepare data for visualization using processed data
     * @param processedData
     * @return
     */
    public JSONObject prepVis(ProcessedData processedData) {
        if (this.currentVisPlugin != null && processedData != null) {
            return this.currentVisPlugin.prepVis(processedData);
        } else {
            return null;
        }
    }


    /**
     * Process data from plugin, nlp extract time/location, and calculate location freq
     * @param unprocessedData
     * @return
     */
    public ProcessedData processData(UnProcessedData unprocessedData) {
        boolean isTabular = unprocessedData.isTabular();
        if (!isTabular) { // if it's pure text, needs to partitioned
            return this.nlpHelper.parseText(unprocessedData.textData());
        } else {
            JSONArray tabularData = unprocessedData.tabularData();
            tabularData = this.nlpHelper.parseTabular(tabularData, unprocessedData.hasTime(), unprocessedData.hasLocation());
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
            return new ProcessedData(tabularData, new JSONObject(locFreqMap));
        }
    }
}
