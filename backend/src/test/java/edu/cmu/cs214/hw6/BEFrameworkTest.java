package edu.cmu.cs214.hw6;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.JsonObject;

import edu.cmu.cs214.hw6.framework.core.UnProcessedData;

public class BEFrameworkTest {
    JsonObject tabularData = new JsonObject();
    UnProcessedData unProcessedData = new UnProcessedData(true, true, true, null, null);
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
