package edu.cmu.cs214.hw6.framework.core;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * needs to be revised as json object
 */
public record UnProcessedData(boolean isTabular, boolean hasTime, boolean hasLocation, String textData, JSONArray tabularData) {

}
