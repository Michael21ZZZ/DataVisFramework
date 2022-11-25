package edu.cmu.cs214.hw6.plugin;

import org.json.*;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

/**
 * Plugin to extract presidents' travel log
 * https://history.state.gov/departmenthistory/travels/president
 */
public class PresidentPlugin implements DataPlugin{

    private WorkFlowFramework framework;
    
    private boolean isTabular;
    private boolean hasTime;
    private boolean hasLocation;
    private String textData;
    private JSONArray tabularData;

    public PresidentPlugin() {
        this.isTabular = true;
        this.hasTime = true;
        this.hasLocation = true;
        this.textData = "";

    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        this.framework = framework;
        
    }
    @Override
    public UnProcessedData getData() {
        UnProcessedData presidentData = new UnProcessedData(
            this.isTabular,
            this.hasTime,
            this.hasLocation,
            this.textData,
            this.tabularData);
        return presidentData;
    }

    @Override
    public void search(String keywords) {
        
    }


    
}