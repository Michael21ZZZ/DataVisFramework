package edu.cmu.cs214.hw6.plugin;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONObject;

import edu.cmu.cs214.hw6.framework.core.ProcessedData;
import edu.cmu.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

public class VisPluginLocFreq implements VisPlugin {
    private JSONObject layout = new JSONObject();
    private List<JSONObject> formattedData = new ArrayList<JSONObject>();
    private JSONObject locFreq;
    @Override
    public JSONObject prepVis(ProcessedData processedData) {
        JSONObject res = new JSONObject();
        this.locFreq = processedData.locationFreq();
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
        
    }

    @Override
    public String getPluginName() {
        return "Location Frequency Bar";
    }
    
}
