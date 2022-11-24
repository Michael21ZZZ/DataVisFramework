package edu.cmu.cs214.hw6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class LocationExtraction {

    public static void main(String[] args) throws IOException {

        InputStream inputStreamTokenizer = new FileInputStream("hw6\\en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);

        // Test string
        String testTexts = "We live in Pittsburgh.\n" +
                "It's a nice city.\n" +
                "But we like China more.\n" + 
                "I'm from Los Angeles";

        // Instantiating the TokenizerME class
        TokenizerME tokenizer = new TokenizerME(tokenModel);
        String tokens[] = tokenizer.tokenize(testTexts);

        // Loading the NER-location moodel
        InputStream inputStreamNameFinder = new FileInputStream("hw6\\en-ner-location.bin");
        TokenNameFinderModel location = new TokenNameFinderModel(inputStreamNameFinder);

        // Instantiating the NameFinderME class
        NameFinderME nameFinder;
        nameFinder = new NameFinderME(location);

        // Finding the names of a location
        Span nameSpans[] = nameFinder.find(tokens);
        // Printing the spans of the locations in the sentence
        for (Span s : nameSpans)
            System.out.println(tokens[s.getStart()]);
    }

}
