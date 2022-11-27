package edu.cmu.cs214.hw6.plugin;

import org.json.*;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

public class ManualPlugin implements DataPlugin{

    private WorkFlowFramework framework;
    private String textData;
    private final boolean isTabular = false;
    private final boolean hasTime = false;
    private final boolean hasLocation = false;
    private final JSONArray tabularData = null;

    @Override
    public void onRegister(WorkFlowFramework framework) {
        this.framework = framework;
        
    }

    @Override
    public UnProcessedData getData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void search(String keywords) {
        // TODO Auto-generated method stub
        
    }
    
}
