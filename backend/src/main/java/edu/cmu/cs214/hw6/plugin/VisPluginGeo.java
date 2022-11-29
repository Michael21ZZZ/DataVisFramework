package edu.cmu.cs214.hw6.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import edu.cmu.cs214.hw6.framework.core.VisPlugin;

public class VisPluginGeo implements VisPlugin {
    private JSONObject layout = new JSONObject();
    private JSONArray coreData = new JSONArray();
    private List<JSONObject> formattedData = new ArrayList<JSONObject>();
    private JSONObject locFreq;
    @Override
    public JSONObject prepVis(JSONObject processedData) {
        JSONObject res = new JSONObject();
        this.coreData = processedData.getJSONArray("coreData");
        this.locFreq = processedData.getJSONObject("locationFreq");
        this.formatData();
        res.put("data", this.formattedData);
        res.put("layout", this.layout);
        return res;
    }

    @Override
    public void setLayout() {
        layout.put("dragmode", "zoom");
        
        layout.put("mapbox", this.getMapBox());
        layout.put("margin", this.getMargin());
    }

    public JSONObject getMapBox() {
        JSONObject mapBox = new JSONObject();
        mapBox.put("style", "scattergeo");
        // add center
        JSONObject center = new JSONObject();
        center.put("lat", 38);
        center.put("lon", -90);
        mapBox.put("center", center);
        // add zoom
        mapBox.put("zoom", 3);
        return mapBox;
    }

    public JSONObject getMargin() {
        JSONObject margin = new JSONObject();
        margin.put("r", 0);
        margin.put("t", 0);
        margin.put("b", 0);
        margin.put("l", 0);
        return margin;
    }

    @Override
    public void formatData() {
        List<String> allText = new ArrayList<String>();
        List<String> allLoc = new ArrayList<String>();
        List<String> allDate = new ArrayList<String>();
        List<Double> allLat = new ArrayList<Double>();
        List<Double> allLon = new ArrayList<Double>();
        for (int i = 0; i < this.coreData.length(); i++) {
            JSONObject row = this.coreData.getJSONObject(i);
            allText.add(row.getString("text"));
            allLoc.add(row.getString("location"));
            allLat.add(row.getDouble("lat"));
            allLon.add(row.getDouble("lng"));
            allDate.add(row.getString("time"));
        }
        JSONObject formattedCoreDate = new JSONObject();
        formattedCoreDate.put("type", "scattermapbox");
        formattedCoreDate.put("text", allText);
        formattedCoreDate.put("lon", allLon);
        formattedCoreDate.put("lat", allLat);
        formattedCoreDate.put("mode", "markers");
        this.formattedData.add(formattedCoreDate);
    }

    
}
