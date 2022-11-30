package edu.cmu.cs214.hw6.framework.core;

import org.json.JSONArray;


public record UnProcessedData(boolean isTabular, boolean hasTime, boolean hasLocation, String textData, JSONArray tabularData) {

}
