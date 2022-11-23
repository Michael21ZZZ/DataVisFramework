package edu.cmu.cs214.hw6.framework.core;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

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

    /**
     * Set current {@link DataPlugin} 
     */
    public void setCurrentPlugin(DataPlugin plugin) {
        this.currentPlugin = plugin;
    }

    public JSONObject fetchData(String keywords) {
        if (this.currentPlugin != null) {
            this.currentPlugin.search(keywords);
            return this.currentPlugin.getData();
        } else {
            return null;
        }
    }
}
