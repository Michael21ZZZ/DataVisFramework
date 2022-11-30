package edu.cmu.cs214.hw6.plugin;

import org.json.*;
import io.github.fastily.jwiki.core.Wiki;
import edu.cmu.cs214.hw6.framework.core.*;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;


public class WikiPlugin implements DataPlugin {

    private boolean isTabular;
    private boolean hasTime;
    private boolean hasLocation;
    private String textData;
    private JSONArray tabularData;

    private static final String PLUGIN_NAME = "Wikipedia";
    private static final String PLUGIN_INSTRUCTION = "Please enter a keyword that is used to be search on Wikipedia. \nFor this plugin, celebrity keywords(Barack Obama) works very well";
    
    public WikiPlugin() {
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
            throw new IllegalArgumentException("Keyword not found. Please change another keyword.");
        }
    }

    /**
     * Temporary getter method
     */
    public String getText() {
        return this.textData;
    }


    @Override
    public String getPluginInstructions() {
        return WikiPlugin.PLUGIN_INSTRUCTION;
    }

    @Override
    public String getPluginName() {
        return WikiPlugin.PLUGIN_NAME;
    }
    
}
