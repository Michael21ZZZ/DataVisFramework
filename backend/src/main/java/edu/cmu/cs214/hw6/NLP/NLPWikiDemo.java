package edu.cmu.cs214.hw6.NLP;

import java.util.List;
import java.util.Properties;

import org.json.JSONArray;

import edu.cmu.cs214.hw6.framework.core.SearchTerm;
import edu.cmu.cs214.hw6.plugin.WikiPlugin;


public class NLPWikiDemo {
    public static void main(String[] args) {
      // generated text from wiki plugin
      WikiPlugin wikiPlugin = new WikiPlugin();
      SearchTerm searchTerm = new SearchTerm("Abraham Lincoln", new JSONArray());
      wikiPlugin.search(searchTerm);
      final String demoText = wikiPlugin.getText();
      NLPHelper nlpHelper = new NLPHelper();
      System.out.println(nlpHelper.parseText(demoText));
    }  
  }

