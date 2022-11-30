package edu.cmu.cs214.hw6.plugin;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

public class VisPluginLocFreq implements VisPlugin {
    private JSONObject layout = new JSONObject();
    private JSONArray coreData = new JSONArray();
    private List<JSONObject> formattedData = new ArrayList<JSONObject>();
    private JSONObject locFreq;
    private WorkFlowFramework framework;
    @Override
    public JSONObject prepVis(JSONObject processedData) {
        JSONObject res = new JSONObject();
        this.coreData = processedData.getJSONArray("coreData");
        this.locFreq = processedData.getJSONObject("locationFreq");
        this.formatData();
        this.setLayout();
        res.put("data", this.formattedData);
        res.put("layout", this.layout);
        return res;
    }

    @Override
    public void setLayout() {
    }



    @Override
    public void formatData() {
        this.formattedData = new ArrayList<JSONObject>();
        List<String> x = new ArrayList<String>();
        List<Integer> y = new ArrayList<Integer>();
        for(String key: this.locFreq.keySet()){
            x.add(key);
            y.add(this.locFreq.getInt(key));
        }
        JSONObject formattedCoreDataInner = new JSONObject();
        formattedCoreDataInner.put("x", x);
        formattedCoreDataInner.put("y", y);
        formattedCoreDataInner.put("type", "bar");
        this.formattedData.add(formattedCoreDataInner);
    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        this.framework = framework;
        
    }

    @Override
    public String getPluginName() {
        return "Location Frequency Bar";
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
