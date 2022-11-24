package edu.cmu.cs214.hw6.framework.core;
import java.util.List;

import org.json.JSONObject;

public record UnProcessedData(boolean isTabular, boolean hasTime, boolean hasLocation, String textData, List<JSONObject> tabularData) {

}
