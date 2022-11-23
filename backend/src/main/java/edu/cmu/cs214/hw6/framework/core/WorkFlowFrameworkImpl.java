package edu.cmu.cs214.hw6.framework.core;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowFrameworkImpl implements WorkFlowFramework{
    private DataPlugin currentPlugin;
    private List<DataPlugin>  registeredPlugins;

    public WorkFlowFrameworkImpl() {
        this.registeredPlugins = new ArrayList<DataPlugin>();
    }

    /**
     * Registers a new {@link DataPlugin} with the game framework
     */
    public void registerPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredPlugins.add(plugin);
    }

    public void fetchData() {
        
    }
}
