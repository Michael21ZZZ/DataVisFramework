package edu.cmu.cs214.hw6;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFrameworkImpl;

public class BEFrameworkTest {
    JSONArray tabDataArray = new JSONArray();
    UnProcessedData unProcessedData;
    @Before
    public void init() {
        JSONObject data1 = new JSONObject();
        data1.put("time", "2022-05-12");
        data1.put("location", "Pittsburgh");
        data1.put("text", "I had a donut.");
        tabDataArray.put(data1);
        JSONObject data2 = new JSONObject();
        data2.put("time", "2022-06-12");
        data2.put("location", "New York");
        data2.put("text", "I went to the Met.");
        tabDataArray.put(data2);
        this.unProcessedData = new UnProcessedData(true, true, true, "", tabDataArray);
    }

    
    @Test
    public void processTest() {
        WorkFlowFrameworkImpl wffi = new WorkFlowFrameworkImpl();
        System.out.println(wffi.processData(this.unProcessedData));

    }
}
