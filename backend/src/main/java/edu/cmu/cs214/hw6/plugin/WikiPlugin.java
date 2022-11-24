package edu.cmu.cs214.hw6.plugin;

import org.json.JSONObject;
import io.github.fastily.jwiki.*;
import io.github.fastily.jwiki.core.Wiki;
import edu.cmu.cs214.hw6.framework.core.*;

public class WikiPlugin implements DataPlugin {

    private WorkFlowFramework framework;
    private String keyword;
    private String textData;
    private final String textOrTabular = "text";
    private final boolean hasTime = false;
    private final boolean hasLocation = false;
    private final JSONObject tabularData = null;

    @Override
    public void onRegister(WorkFlowFramework framework) {
        this.framework = framework;
    }

    @Override
    public UnProcessedData getData() {
        UnProcessedData wikiData = new UnProcessedData(
            this.textOrTabular,
            this.hasTime,
            this.hasLocation,
            this.textData,
            this.tabularData);
        return wikiData;
    }

    public void search(String keywords) {
        Wiki wiki = new Wiki.Builder().build();
        this.textData = wiki.getTextExtract(this.keyword) ;
    }
}
