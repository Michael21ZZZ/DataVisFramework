package edu.cmu.cs214.hw6.NLP;

import edu.cmu.cs214.hw6.framework.core.SearchTerm;
import edu.cmu.cs214.hw6.plugin.WikiPlugin;


public class NLPWikiDemo {
    public static void main(String[] args) {
      // generated text from wiki plugin
      WikiPlugin wikiPlugin = new WikiPlugin();
      SearchTerm searchTerm = new SearchTerm("Barack Obama");
      wikiPlugin.search(searchTerm);
      final String demoText = wikiPlugin.getText();
      System.out.println(demoText);
      NLPHelper nlpHelper = new NLPHelper();
      System.out.println(nlpHelper.parseText(demoText));
    }  
  }

