package edu.cmu.cs214.hw6;

import org.junit.*;
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
