package edu.cmu.cs214.hw6;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;

import edu.cmu.cs214.hw6.plugin.PresidentPlugin;
/**
 * 
 */
public class PresidentPluginTest {

    PresidentPlugin testPlugin = new PresidentPlugin();
    String inputName1 = "harding-warren-g.xml";

    @Test
    public void testLoadTravelLog()throws ParserConfigurationException, SAXException, IOException {
        JSONArray result1 = testPlugin.loadTravelLogs(inputName1);
        JSONObject jo0 = result1.getJSONObject(0);
        assertEquals("1920-11-24", jo0.getString("time"));
        assertEquals("Colon, Baihos", jo0.getString("location"));
        assertEquals("Informal visit to Canal Zone. [Visit made as President-elect.]", jo0.getString("text"));

        //JSONObject jo2 = result1.getJSONObject(2);
    }



    @Test
    public void whenGetElementByTag_thenSuccess() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File("president-travels/harding-warren-g.xml"));
        doc.getDocumentElement().normalize();
    
        NodeList nodeList = doc.getElementsByTagName("trip");
        Node first = nodeList.item(0);

        assertEquals(3, nodeList.getLength());
        assertEquals(Node.ELEMENT_NODE, first.getNodeType());
        assertEquals("trip", first.getNodeName());        
    }

}
