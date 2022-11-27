package edu.cmu.cs214.hw6.NLP;
import edu.stanford.nlp.pipeline.*;

import java.util.*;

public class NLPSplitSent {

    public static void main(String[] args) {
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize, ssplit");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument doc = new CoreDocument(NLPDemo.demoText);
        // annotate
        pipeline.annotate(doc);
        // display sentences
        for (CoreSentence sent : doc.sentences()) {
            System.out.println(sent.text());
        }
    }
}
