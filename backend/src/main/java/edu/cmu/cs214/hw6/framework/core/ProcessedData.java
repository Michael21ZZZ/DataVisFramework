package edu.cmu.cs214.hw6.framework.core;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * needs to be revised as json object
 */
public record ProcessedData (JSONArray coreData, JSONObject locationFreq) { }
