package edu.cmu.cs214.hw6;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw6.framework.core.UnProcessedData;

public class BEFrameworkTest {
    JSONArray tabDataArray = new JSONArray();
    
    @Before
    public void init() {
        JSONObject data1 = new JSONObject();
        data1.put("time", "2026-05-12");
        tabDataArray.put(data1);
    }

    UnProcessedData unProcessedData = new UnProcessedData(true, true, true, "", tabDataArray);
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
