package edu.cmu.cs214.hw6.plugin;

import org.json.JSONObject;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

import io.github.fastily.jwiki.*;
import io.github.fastily.jwiki.core.Wiki;;

public class WikiPluginTest {

    public static void main(String[] args) {
        Wiki wiki = new Wiki.Builder().build();
        //System.out.println(wiki.getPageText("Elon Musk") );
        System.out.println(wiki.getTextExtract("Elon Musk") );
    }
}

