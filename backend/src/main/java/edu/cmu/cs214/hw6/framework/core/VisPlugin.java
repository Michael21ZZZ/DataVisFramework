package edu.cmu.cs214.hw6.framework.core;

import org.json.JSONObject;

public interface VisPlugin {
    /**
     * Further process the data. The output should align with the plotly documentation 
     * https://plotly.com/javascript/reference/index/
     * 
     * @param processedData
     * @return the result should include data and layout {"data": JSONArray[], "layout":JSONObject}
     */
    public JSONObject prepVis(JSONObject processedData);
    
    /**
     * Set the layout of this vis, make change to the local variable layout
     */
    public void setLayout();

    /**
     * A helper function that format the processed data into a JSONArray required by plotly
     */
    public void formatData();

    /**
     * Register the framework to the plugin
     * @param framework
     */
    public void onRegister(WorkFlowFramework framework);

    /**
     * Gets the name of the plug-in game.
     * @return Name for specific plugins
     */
    String getPluginName();
}
