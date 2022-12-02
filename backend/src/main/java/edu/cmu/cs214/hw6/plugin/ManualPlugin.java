package edu.cmu.cs214.hw6.plugin;

import org.json.*;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;

public class ManualPlugin implements DataPlugin{

    private boolean isTabular;
    private boolean hasTime;
    private boolean hasLocation;
    private String textData;
    private JSONArray tabularData;
    private static final String PLUGIN_NAME = "Manual";
    private static final String PLUGIN_INSTRUCTION = "Please enter any text that includes time and space information. \n";

    public ManualPlugin() {
        this.isTabular = false;
        this.hasTime = false;
        this.hasLocation = false;
        this.textData = new String();
        this.tabularData = new JSONArray();
    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        
    }

    @Override
    public UnProcessedData getData() {
        UnProcessedData manualData = new UnProcessedData(
            this.isTabular,
            this.hasTime,
            this.hasLocation,
            this.textData,
            this.tabularData);
        return manualData;
    }

    @Override
    public void search(SearchTerm searchTerm) {
        this.textData = searchTerm.keyword();
    }

    @Override
    public String getPluginInstructions() {
        return ManualPlugin.PLUGIN_INSTRUCTION;
    }

    @Override
    public String getPluginName() {
        return ManualPlugin.PLUGIN_NAME;
    }
    
}
