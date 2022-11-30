package edu.cmu.cs214.hw6;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;

import edu.cmu.cs214.hw6.framework.core.SearchTerm;
import edu.cmu.cs214.hw6.plugin.WikiPlugin;

public class WikiPluginTest {
    WikiPlugin wikiTest = new WikiPlugin();
    @Test
    public void wikiTest() {
        wikiTest.search(new SearchTerm("Andrew Carnegie"));
        System.out.println(wikiTest.getText());
    } 
}
