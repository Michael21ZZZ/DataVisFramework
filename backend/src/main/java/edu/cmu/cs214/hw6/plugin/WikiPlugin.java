package edu.cmu.cs214.hw6.plugin;

import org.json.*;
import io.github.fastily.jwiki.core.Wiki;
import edu.cmu.cs214.hw6.framework.core.*;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;

public class WikiPlugin implements DataPlugin {

    private WorkFlowFramework framework;
    private boolean isTabular;
    private boolean hasTime;
    private boolean hasLocation;
    private String textData;
    private JSONArray tabularData;
    
    public WikiPlugin() {
        this.isTabular = false;
        this.hasTime = false;
        this.hasLocation = false;
        this.tabularData = null;
    }

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
    public void search(SearchTerm searchTerm) {
        String keyword = searchTerm.keyword();
        Wiki wiki = new Wiki.Builder().build();
        this.textData = wiki.getTextExtract(keyword);
        if (this.textData == null) {
            this.textData = "";
        }
    }
}
