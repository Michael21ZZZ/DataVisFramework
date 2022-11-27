package edu.cmu.cs214.hw6.plugin;

import org.json.*;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;

public class ManualPlugin implements DataPlugin{

    private WorkFlowFramework framework;
    private boolean isTabular;
    private boolean hasTime;
    private boolean hasLocation;
    private String textData;
    private JSONArray tabularData;

    public ManualPlugin() {
        this.isTabular = true;
        this.hasTime = true;
        this.hasLocation = true;
        this.textData = "";
        this.tabularData = new JSONArray();
    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        this.framework = framework;
        
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
        this.tabularData = searchTerm.tabularData();
    }
    
}
