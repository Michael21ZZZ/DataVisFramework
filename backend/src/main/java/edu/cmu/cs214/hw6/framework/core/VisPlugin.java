package edu.cmu.cs214.hw6.framework.core;

import org.json.JSONObject;

public interface VisPlugin {
    /**
     * FUrther process the data; the result should include data and layout
     * @return
     */
    public JSONObject prepVis(JSONObject processedData);
    
    /**
     * Set the layout of this vis
     */
    public void setLayout();

    /**
     * Format Data
     */
    public void formatData();
}
