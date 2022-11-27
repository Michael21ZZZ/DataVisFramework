package edu.cmu.cs214.hw6.framework.core;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;



public record SearchTerm(String keyword, JSONArray tabularData) {

}