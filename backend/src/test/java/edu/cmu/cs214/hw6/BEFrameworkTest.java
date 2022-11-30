package edu.cmu.cs214.hw6;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.framework.core.SearchTerm;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFrameworkImpl;
import edu.cmu.cs214.hw6.plugin.WikiPlugin;

public class BEFrameworkTest {
    JSONArray tabDataArray = new JSONArray();
    UnProcessedData unProcessedData;

    public void addUnProcessedData(String time, String loc, String text) {
        JSONObject data1 = new JSONObject();
        data1.put("time", time);
        data1.put("location", loc);
        data1.put("text", text);
        tabDataArray.put(data1);
    }
    @Before
    public void init() {
        addUnProcessedData("2022-05-12", "Pittsburgh", "I had a donut.");
        addUnProcessedData("2022-06-12", "New York", "I went to the Met.");
        addUnProcessedData("2023-09-29", "Chicago", "Peter and I went to Ed Sheeran's concert.");
        addUnProcessedData("2024-02-1", "Pittsburgh", "The Civil War 2 broke out.");
        this.unProcessedData = new UnProcessedData(true, true, true, "", tabDataArray);
    }

}
