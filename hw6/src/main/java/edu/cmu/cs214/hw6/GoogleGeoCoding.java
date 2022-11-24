package edu.cmu.cs214.hw6;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GoogleGeoCoding {
    public static void main(String[] args) throws ApiException, InterruptedException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCDAYlOl4ongbn5JV4nhg6QBe4bKCo2NWE")
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context, "Guangzhou").await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0]));

        // Invoke .shutdown() after your application is done making requests
        context.shutdown();
    }
}
