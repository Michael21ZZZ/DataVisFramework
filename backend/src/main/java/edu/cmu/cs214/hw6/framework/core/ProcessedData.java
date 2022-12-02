package edu.cmu.cs214.hw6.framework.core;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * needs to be revised as json object
 */
public record ProcessedData (JSONArray coreData, JSONObject locationFreq) { }
