package edu.cmu.cs214.hw6.plugin;

import org.json.*;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

/**
 * Plugin to extract presidents' travel log
 * https://history.state.gov/departmenthistory/travels/president
 * https://www.baeldung.com/java-xerces-dom-parsing
 * https://www.baeldung.com/java-org-json
 * 
 */
public class PresidentPlugin implements DataPlugin{

    private WorkFlowFramework framework;
    
    private boolean isTabular;
    private boolean hasTime;
    private boolean hasLocation;
    private String textData;
    private JSONArray tabularData;

    public PresidentPlugin() {
        this.isTabular = true;
        this.hasTime = true;
        this.hasLocation = true;
        this.textData = "";
        this.tabularData = new JSONArray();
    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        this.framework = framework;
        
    }
    @Override
    public UnProcessedData getData() {
        UnProcessedData presidentData = new UnProcessedData(
            this.isTabular,
            this.hasTime,
            this.hasLocation,
            this.textData,
            this.tabularData);
        return presidentData;
    }

    @Override
    public void search (String keywords) {
        try {
            this.tabularData = loadTravelLogs(keywords);
        } catch(ParserConfigurationException e) {
            System.out.println(e.getMessage());  
        } catch(SAXException e) {
            System.out.println(e.getMessage());  
        } catch(IOException e) {
            System.out.println(e.getMessage());  
        }
    }

    /**
     * 
     * @param presidentStandardName
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public JSONArray loadTravelLogs(String presidentStandardName) throws ParserConfigurationException, SAXException, IOException {
        JSONArray tabularData = new JSONArray();
        // prepare the document
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //StringBuilder urlPrefix = new StringBuilder("backend/president-travels/"); 
        StringBuilder urlPrefix = new StringBuilder("president-travels/"); 
        StringBuilder standardName = new StringBuilder("harding-warren-g.xml");       
        String filePath =  urlPrefix.append(standardName).toString();  
        Document doc = builder.parse(new File(filePath));
        doc.getDocumentElement().normalize();
        // get node element
        NodeList nodeList = doc.getElementsByTagName("trip");
        Integer visitTimes = nodeList.getLength();
        for (int i = 0; i < visitTimes; i++) {
            Node visit = doc.getElementsByTagName("trip").item(i);
            NodeList attrList = visit.getChildNodes();
            Node startDate = attrList.item(7);
            Node location = attrList.item(13);
            Node remarks = attrList.item(15);
            JSONObject jo = new JSONObject();
            jo.put("time", startDate.getTextContent());
            jo.put("location", location.getTextContent());
            jo.put("text", remarks.getTextContent());
            tabularData.put(jo);
        }

        return tabularData;
    }







}

