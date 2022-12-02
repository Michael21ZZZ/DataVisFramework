package edu.cmu.cs214.hw6.plugin;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

public class VisPluginGeo implements VisPlugin {
    private JSONObject layout = new JSONObject();
    private JSONArray coreData = new JSONArray();
    private List<JSONObject> formattedData = new ArrayList<JSONObject>();
    @Override
    public JSONObject prepVis(JSONObject processedData) {
        JSONObject res = new JSONObject();
        this.coreData = processedData.getJSONArray("coreData");
        processedData.getJSONObject("locationFreq");
        this.formatData();
        this.setLayout();
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
        mapBox.put("style", "open-street-map");
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
        this.formattedData = new ArrayList<JSONObject>();
        List<String> allText = new ArrayList<String>();
        List<String> allLoc = new ArrayList<String>();
        List<String> allDate = new ArrayList<String>();
        List<Double> allLat = new ArrayList<Double>();
        List<Double> allLon = new ArrayList<Double>();
        List<Integer> allYear = new ArrayList<Integer>();
        for (int i = 0; i < this.coreData.length(); i++) {
            JSONObject row = this.coreData.getJSONObject(i);
            String date = row.getString("time");
            allDate.add(date);
            String loc = row.getString("location");
            allLoc.add(loc);
            allText.add(date + " " + loc + " " + row.getString("text"));
            allLat.add(row.getDouble("lat"));
            allLon.add(row.getDouble("lng"));
            
            int year = Integer.parseInt(date.substring(0, 4));
            allYear.add(year);
        }
        JSONObject formattedCoreData = new JSONObject();
        formattedCoreData.put("type", "scattermapbox");
        formattedCoreData.put("text", allText);
        formattedCoreData.put("lon", allLon);
        formattedCoreData.put("lat", allLat);
        formattedCoreData.put("mode", "markers");
        JSONObject colorbar = new JSONObject();
        colorbar.put("title", "Date");
        JSONObject markers = new JSONObject();
        markers.put("color", allYear);
        markers.put("colorbar", colorbar);
        formattedCoreData.put("marker", markers);
        this.formattedData.add(formattedCoreData);
    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        
    }

    @Override
    public String getPluginName() {
        return "GeoMap";
    }

    public static void main(String[] args) {
        JSONArray coreData = new JSONArray();
        JSONObject row1 = new JSONObject();
        row1.put("lat", 30);
        row1.put("lng", 40);
        row1.put("text", "hamburght");
        row1.put("time", "1999-01-02");
        row1.put("location", "New York");
        coreData.put(row1);
        JSONObject row2 = new JSONObject();
        row2.put("lat", 40);
        row2.put("lng", 50);
        row2.put("text", "pizza");
        row2.put("time", "2000-01-02");
        row2.put("location", "Chicago");
        coreData.put(row2);
        JSONObject locFreq = new JSONObject();
        locFreq.put("MIAMI", 1);
        JSONObject processedData = new JSONObject();
        processedData.put("coreData", coreData);
        processedData.put("locationFreq", locFreq);
        VisPluginGeo vpg = new VisPluginGeo();
        System.out.println(vpg.prepVis(processedData));
    }
    
}
