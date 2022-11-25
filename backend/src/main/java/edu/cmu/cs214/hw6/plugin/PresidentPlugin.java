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

    @Override
    public void onRegister(WorkFlowFramework framework) {
        // TODO Auto-generated method stub
        
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