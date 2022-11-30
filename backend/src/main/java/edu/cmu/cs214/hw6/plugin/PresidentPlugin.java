package edu.cmu.cs214.hw6.plugin;

import org.json.*;

import edu.cmu.cs214.hw6.framework.core.DataPlugin;
import edu.cmu.cs214.hw6.framework.core.UnProcessedData;
import edu.cmu.cs214.hw6.framework.core.WorkFlowFramework;
import edu.cmu.cs214.hw6.framework.core.SearchTerm;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

import java.util.ArrayList;

/**
 * Plugin to extract presidents' travel log
 * https://history.state.gov/departmenthistory/travels/president
 * https://www.baeldung.com/java-xerces-dom-parsing
 * https://www.baeldung.com/java-org-json
 * 
 */
public class PresidentPlugin implements DataPlugin{

    private static final String PLUGIN_NAME = "President";
    private static final String PLUGIN_INSTRUCTION = "Please enter the last name and the first name of a president of United States." 
    + "For example, you can enter trump donald to search for Donald Trump."
    + "If you are searching George H. W. Bush, please enter bush george h w." 
    + "If you are searching George W. Bush, please enter bush george w. "
    + "Source: https://history.state.gov/departmenthistory/travels/president.";
    
    private boolean isTabular;
    private boolean hasTime;
    private boolean hasLocation;
    private String textData;
    private JSONArray tabularData;
    private ArrayList<String> preNameList;
    private ArrayList<String> preFileNameList;


    public PresidentPlugin() {
        this.isTabular = true;
        this.hasTime = true;
        this.hasLocation = true;
        this.textData = "";
        this.tabularData = new JSONArray();
        this.getPresidentNames();

    }

    @Override
    public void onRegister(WorkFlowFramework framework) {
        
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
    public void search (SearchTerm searchTerm) {
        String keyword = searchTerm.keyword();
        try {
            String standardFileName = transformName(keyword);
            this.tabularData = loadTravelLogs(standardFileName);
        } catch(ParserConfigurationException e) {
            System.out.println(e.getMessage());  
        } catch(SAXException e) {
            System.out.println(e.getMessage());  
        } catch(IOException e) {
            System.out.println(e.getMessage());  
        }
    }

    /**
     * Load travel logs with given president name from local XML files.
     * @param presidentStandardName
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private JSONArray loadTravelLogs(String standardFileName) throws ParserConfigurationException, SAXException, IOException {
        JSONArray tabularData = new JSONArray();
        // prepare the document
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //StringBuilder urlPrefix = new StringBuilder("backend/president-travels/"); 
        StringBuilder urlPrefix = new StringBuilder("president-travels/"); 
        StringBuilder standardName = new StringBuilder(standardFileName);       
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

    @Override
    public String getPluginInstructions() {
        return PresidentPlugin.PLUGIN_INSTRUCTION;
    }

    @Override
    public String getPluginName() {
        return PresidentPlugin.PLUGIN_NAME;
    }
    /**
     * Transform users'input into standard file name to locate which president. 
     * @param inputName
     * @return
     */
    private String transformName(String inputName) {
        String newName= inputName.replaceAll("[^A-Za-z]+", "").toLowerCase();
        Integer lenInputName = newName.length();
        ArrayList<String> selectedNameList = new ArrayList<String>();
        for (int i = 0; i < this.preFileNameList.size(); i++) {
            String standardName = this.preNameList.get(i);
            selectedNameList.add(standardName.substring(0, lenInputName));
        }

        if (newName.equals("bushgeorge")) {
            throw new IllegalArgumentException("Please specify which President Bush you are referring to. ");
        } else if (selectedNameList.contains(newName)) {
            return this.preFileNameList.get(selectedNameList.indexOf(newName));
        } else {
            throw new IllegalArgumentException("No president found. Please check your input.");
        }
   
    } 

    /**
     * 
     * @return An array list that contains file names for all president's travel file. 
     * Examples: obamabarack
     */
    private void getPresidentNames() {
        ArrayList<String> preFileNameList = new ArrayList<String>();
        ArrayList<String> preNameList = new ArrayList<String>();
        File folder = new File("president-travels/");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();
            String standardName = fileName.replaceAll("[^A-Za-z]+", "").toLowerCase();
            preFileNameList.add(fileName);
            preNameList.add(standardName);
        }

        this.preNameList = preNameList;
        this.preFileNameList = preFileNameList;
    }
    /**
     * 
     * @return tabularData. 
     */
    public JSONArray getTabularData() {
        return this.tabularData;
    }

}

