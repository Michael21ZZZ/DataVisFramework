package edu.cmu.cs214.hw6.framework.core;

public interface WorkFlowFramework {
    /**
     * Registers a new {@link DataPlugin} with the game framework
     */
    public void registerDataPlugin(DataPlugin plugin);

    /**
     * Set current {@link DataPlugin} 
     */
    public void setCurrentDataPlugin(DataPlugin plugin);

    /**
     * Get data using data plugin
     * @param searchTerm
     * @return
     */
    public UnProcessedData fetchData(SearchTerm searchTerm);

    /**
     * Registers a new {@link VisPlugin} with the framework
     */
    public void registerVisPlugin(VisPlugin plugin);

    /**
     * Set current {@link VisPlugin} 
     */
    public void setCurrentVisPlugin(VisPlugin plugin);

    

}
