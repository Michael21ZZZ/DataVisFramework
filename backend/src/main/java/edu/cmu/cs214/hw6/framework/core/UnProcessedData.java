package edu.cmu.cs214.hw6.framework.core;
import org.json.JSONObject;

public record UnProcessedData(String textOrTabular, boolean hasTime, boolean hasLocation, String textData, JSONObject tabularData) {

}
