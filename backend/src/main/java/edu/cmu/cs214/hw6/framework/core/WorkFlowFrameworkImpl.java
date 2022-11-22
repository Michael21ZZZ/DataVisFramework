package edu.cmu.cs214.hw6.framework.core;

import java.util.List;

public class WorkFlowFrameworkImpl implements WorkFlowFramework{
    private DataPlugin currentPlugin;
    private List<DataPlugin>  registeredPlugins;
    /**
     * Registers a new {@link GamePlugin} with the game framework
     */
    public void registerPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredPlugins.add(plugin);
    }
}
