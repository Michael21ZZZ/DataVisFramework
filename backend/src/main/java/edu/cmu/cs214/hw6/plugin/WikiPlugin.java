package edu.cmu.cs214.hw6.plugin;

import org.json.JSONObject;
import io.github.fastily.jwiki.*;
import io.github.fastily.jwiki.core.Wiki;
import edu.cmu.cs214.hw6.framework.core.*;

public class WikiPlugin implements DataPlugin {

    private WorkFlowFramework framework;
    private String keyword;
    private String text;


    
    @Override
    public void onRegister(WorkFlowFramework framework) {
        this.framework = framework;
    }

    @Override
    public UnProcessedData getData() {

        UnProcessedData data = new UnProcessedData(null, false, false, null, null);
        return data;
    }

    private void search(String keyword) {
        Wiki wiki = new Wiki.Builder().build();
        this.text = wiki.getTextExtract("Elon Musk") ;
    }
}
