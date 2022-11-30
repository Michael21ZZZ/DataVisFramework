package edu.cmu.cs214.hw6.framework.core;


public interface DataPlugin {

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the game has begun (if necessary).
     */
    void onRegister(WorkFlowFramework framework);
    
    /**
     * Every class that implements this interface should have a field of 
     * JSONObject to store the data to be processed by framework
     * This method will return that field of JSONObject to the framework
     */
    UnProcessedData getData();

    /**
     * Perform search based on searchTerm
     * @param searchTerm Words used for search
     */
    void search(SearchTerm searchTerm);

    /**
     * Get the instruction for this plugin
     * @return Instruction for specific plugins
     */
    String getPluginInstructions();

    /**
     * Gets the name of the plug-in game.
     * @return Name for specific plugins
     */
    String getPluginName();       
}
