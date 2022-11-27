package edu.cmu.cs214.hw6.NLP;
import java.io.IOException;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GoogleGeoCoding {
    private final String apiKey = "AIzaSyCDAYlOl4ongbn5JV4nhg6QBe4bKCo2NWE";

    /**
     * Get the coordinates of a place; return  {lat: 999, lng: 999} if not found
     * @param keyword a place to search
     * @return coordinate JSONObject. {lat: 999, lng: 999} if not found
     */
    public JSONObject getCord(String keyword) {
        JSONObject defaultJO = new JSONObject();
        defaultJO.put("lat", 0);
        defaultJO.put("lng", 0);
        JSONObject output = new JSONObject();
        if (keyword == null || keyword.strip().length() == 0) {
            return defaultJO;
        }
        GeoApiContext context = null;
        try {
            context = new GeoApiContext.Builder()
                .apiKey(this.apiKey)
                .build();
            GeocodingResult[] results = GeocodingApi.geocode(context, keyword).await();
            if (results.length != 0) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String outputStr = gson.toJson(results[0]);
                JSONObject jsonObject = new JSONObject(outputStr);
                output = jsonObject.getJSONObject("geometry").getJSONObject("location");
            } else { // no results from google map
                output = defaultJO;
            }
            
        } catch (ApiException | InterruptedException | IOException e) {
            output = defaultJO;
        } finally {
            // Invoke .shutdown() after your application is done making requests
            if (context != null) {
                context.shutdown();
            }
        }
        return output;
    }

    public static void main(String[] args) throws ApiException, InterruptedException, IOException {
        GoogleGeoCoding ggc = new GoogleGeoCoding();
        JSONObject res = ggc.getCord("New Orleans");
        System.out.println(res);
    }
}
