package edu.cmu.cs214.hw6.plugin;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.cs214.hw6.framework.core.ProcessedData;
import edu.cmu.cs214.hw6.framework.core.VisPlugin;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

public class VisPluginTimeline implements VisPlugin {
    private JSONObject layout = new JSONObject();
    private JSONArray coreData = new JSONArray();
    private List<JSONObject> formattedData = new ArrayList<JSONObject>();
    @Override
    public JSONObject prepVis(ProcessedData processedData) {
        JSONObject res = new JSONObject();
        this.coreData = processedData.coreData();
        this.formatData();
        this.setLayout();
        res.put("data", this.formattedData);
        res.put("layout", this.layout);
        return res;
    }


    @Override
    public void setLayout() {
        layout.put("xaxis", this.getAxis());
        layout.put("dragmode", "zoom");
        layout.put("margin", this.getMargin());
        layout.put("width", 600);
        layout.put("height", 600);
        layout.put("paper_bgcolor", "rgb(254, 247, 234)");
        layout.put("plot_bgcolor", "rgb(254, 247, 234)");
        layout.put("hovermode", "closest");
    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        
    }

    @Override
    public String getPluginName() {
        return "Timeline";
    }

    @Override
    public void formatData() {
        this.formattedData = new ArrayList<JSONObject>();
        List<String> allLoc = new ArrayList<String>();
        List<Integer> allDate = new ArrayList<Integer>();

        for (int i = 0; i < this.coreData.length(); i++) {
            JSONObject row = this.coreData.getJSONObject(i);
            allLoc.add(row.getString("location"));
            String yearStr = row.getString("time").substring(0, 4);
            Integer year = Integer.parseInt(yearStr);
            allDate.add(year);
        }
        JSONObject formattedCoreDate = new JSONObject();

        formattedCoreDate.put("type", "scatter");
        formattedCoreDate.put("x", allDate);
        formattedCoreDate.put("y", allLoc);
        formattedCoreDate.put("mode", "markers");
        this.formattedData.add(formattedCoreDate);
    }

    public JSONObject getMargin() {
        JSONObject margin = new JSONObject();
        margin.put("r", 140);
        margin.put("t", 40);
        margin.put("b", 50);
        margin.put("l", 80);
        return margin;
    }

    private JSONObject getAxis() {
        JSONObject axisObject = new JSONObject();
        JSONObject font = new JSONObject();
        JSONObject tickfont = new JSONObject();
        axisObject.put("showgrid", false);
        axisObject.put("showline", true);
        axisObject.put("linecolor", "(102, 102, 102)");
        axisObject.put("autotick", false);
        axisObject.put("dtick", 10);
        axisObject.put("ticks", "outside");
        axisObject.put("tickcolor", "rgb(102, 102, 102)");
        font.put("color", "rgb(102, 102, 102)");
        tickfont.put("font", font);
        axisObject.put("tickfont", tickfont);

        return axisObject;
    }

}
