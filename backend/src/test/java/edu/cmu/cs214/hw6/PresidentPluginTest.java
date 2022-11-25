package edu.cmu.cs214.hw6;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

import org.junit.*;
import static org.junit.Assert.*;

public class PresidentPluginTest {

    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File("president-travels/roosevelt-theodore.xml"));
        doc.getDocumentElement().normalize();

    }

    @Test
    public void whenGetElementByTag_thenSuccess() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File("president-travels/roosevelt-theodore.xml"));
        doc.getDocumentElement().normalize();
    
        NodeList nodeList = doc.getElementsByTagName("trip");
        Node first = nodeList.item(0);

        assertEquals(1, nodeList.getLength());
        assertEquals(Node.ELEMENT_NODE, first.getNodeType());
        assertEquals("trip", first.getNodeName());        
    }

    

}
