package edu.cmu.cs214.hw6.framework.core;
import org.json.JSONArray;
import org.json.JSONObject;



public interface DataPlugin {

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the game has begun (if necessary).
     */
    public void onRegister(WorkFlowFramework framework);
    
    /**
     * Every class that implements this interface should have a field of 
     * JSONObject to store the data to be processed by framework
     * This method will return that field of JSONObject to the framework
     */
    public UnProcessedData getData();

    /**
     * Perform search 
     * @param keywords Words used for search
     */
    public void search(String keywords);
        
}