package edu.cmu.cs214.hw6;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.json.JSONObject;
import org.junit.Test;


import edu.cmu.cs214.hw6.NLP.GoogleGeoCoding;

public class GeoStubTest {
    @Test
    public void GeoStubTest1() {
        GoogleGeoCoding mockedGGC = mock(GoogleGeoCoding.class);
        JSONObject mockedCord = new JSONObject();
        mockedCord.put("lat", 32); 
        mockedCord.put("lng", 21);
        when(mockedGGC.getCord("Pittsburgh")).thenReturn(mockedCord);
        assertEquals(mockedCord, mockedGGC.getCord("Pittsburgh"));
    }
}
