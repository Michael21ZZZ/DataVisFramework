package edu.cmu.cs214.hw6.plugin;

import org.json.*;
import io.github.fastily.jwiki.core.Wiki;
import edu.cmu.cs214.hw6.framework.core.*;

public class WikiPlugin implements DataPlugin {

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
        UnProcessedData wikiData = new UnProcessedData(
            this.isTabular,
            this.hasTime,
            this.hasLocation,
            this.textData,
            this.tabularData);
        return wikiData;
    }

    /**
     * Search the keyword on wikipedia and updated the textData.
     * If there is not matching keyword, the textData would be an empty string. 
     */
    public void search(String keywords) {
        Wiki wiki = new Wiki.Builder().build();
        this.textData = wiki.getTextExtract(keywords);
        if (this.textData == null) {
            this.textData = "";
        }
    }
}
