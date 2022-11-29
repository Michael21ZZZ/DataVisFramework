package edu.cmu.cs214.hw6.NLP;

import java.util.List;
import java.util.Properties;

import org.json.JSONArray;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.TimeAnnotations;
import io.github.fastily.jwiki.core.Wiki;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;
import edu.cmu.cs214.hw6.plugin.WikiPlugin;


public class NLPWikiDemo {
    public static void main(String[] args) {
      // generated text from wiki plugin
      WikiPlugin wikiPlugin = new WikiPlugin();
      SearchTerm searchTerm = new SearchTerm("Andrew Carnegie");
      wikiPlugin.search(searchTerm);
      final String demoText = wikiPlugin.getText();

      // set up pipeline properties
      Properties props = new Properties();
      props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
  
      // set up pipeline
      long start = System.currentTimeMillis();
      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
      long finish = System.currentTimeMillis();
      long timeElapsed = finish - start;
      System.out.println("Set up pipeline takes:" + timeElapsed);
      CoreDocument doc = new CoreDocument(demoText);

      // annotate the document
      pipeline.annotate(doc);
      List<CoreSentence> sentences = doc.sentences();    
      // make an example document
      for (CoreSentence sentence: sentences) {
        // annotate the document
        System.out.println("Sentence: " + sentence.text());
        sentence.wrapEntityMentions();
        for (CoreEntityMention em : sentence.entityMentions())
          System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType() + "\t" + "temporal value: " + em.coreMap().get(TimeAnnotations.TimexAnnotation.class));
      } 
    }  
  }

