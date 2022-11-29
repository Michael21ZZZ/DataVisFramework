package edu.cmu.cs214.hw6.NLP;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.TimeAnnotations;

import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;



public class NLPHelper {
    private final String[] nerTypeLoc = {"CITY", "STATE_OR_PROVINCE", "COUNTRY"};

    public static void main(String[] args) {
        NLPHelper nlpHelper = new NLPHelper();
        nlpHelper.parseText(NLPDemo.demoText);
    }
    public JSONObject parseText(String article) {
        GoogleGeoCoding ggc = new GoogleGeoCoding();
        Map<String, Integer> locFreqMap = new HashMap<String, Integer>();
        // set up pipeline properties
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        // set up pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument doc = new CoreDocument(article);
        // annotate the document
        pipeline.annotate(doc);
        List<CoreSentence> sentences = doc.sentences();    
        JSONArray tabularData = new JSONArray();

        // initialize date; scan through the article and find the earliest date; if not; set it to 2020-01-01
        int earliestYear = 9999;
        String prevDateStr = "9999-99-99";
        for (CoreEntityMention em : doc.entityMentions()) {
            if (em.entityType().equals("DATE")) { // a date em
                String dateVal = em.coreMap().get(TimeAnnotations.TimexAnnotation.class).value();
                dateVal = DateReg.dateReg(dateVal, prevDateStr);
                prevDateStr = dateVal;
                int year = Integer.parseInt(dateVal.substring(0, 4));
                if (year < earliestYear) {
                    earliestYear = year;
                }
            }
        }
        earliestYear = Math.min(earliestYear, 9999);
        String prevDate = Integer.toString(earliestYear) + "-01-01";
        JSONObject currentSent = new JSONObject();
        currentSent.put("text", "");
        currentSent.put("location", "");
        currentSent.put("lng", 0);
        currentSent.put("lat", 0);
        currentSent.put("time", prevDate);
        for (CoreSentence sentence: sentences) {
            String sentText = sentence.text();
            System.out.println("Sentence: " + sentence.text());
            List<NERLoc> nERLocList = new ArrayList<NERLoc>();
            List<NERDate> nERDateList = new ArrayList<NERDate>();
            for (CoreEntityMention em : sentence.entityMentions()) {
                if (Arrays.asList(this.nerTypeLoc).contains(em.entityType())) { // a location em
                    nERLocList.add(new NERLoc(em.entityType(), em.text()));
                } else if (em.entityType().equals("DATE")) { // a date em
                    String dateVal = em.coreMap().get(TimeAnnotations.TimexAnnotation.class).value();
                    dateVal = DateReg.dateReg(dateVal, prevDate);
                    prevDate = dateVal;
                    NERDate nERDate = new NERDate(dateVal);
                    nERDateList.add(nERDate);
                }
            }
            Collections.sort(nERDateList, Collections.reverseOrder());
            Collections.sort(nERLocList,  Collections.reverseOrder());
            System.out.println(nERLocList);
            System.out.println(nERDateList);
            currentSent.put("text", currentSent.getString("text")+ sentText);
            if (!nERDateList.isEmpty() || !nERLocList.isEmpty()) { // either a new location or a new date means a new sentence partition
                if (!nERDateList.isEmpty()) { // update date
                    currentSent.put("time", nERDateList.get(0));
                }
                if (!nERLocList.isEmpty()) { // update location
                    String location = nERLocList.get(0).getLocVal();
                    System.out.println(location);
                    currentSent.put("location", location);
                    JSONObject coord = ggc.getCord(location);
                    currentSent.put("lng", coord.getDouble("lng"));
                    currentSent.put("lat", coord.getDouble("lat"));
                    locFreqMap.put(location, locFreqMap.getOrDefault(location, 0) + 1);
                }
                // put this sentence into the new partition
                tabularData.put(new JSONObject(currentSent.toString()));
                // clear the text
                currentSent.put("text", "");
            }
        } 
        if (!currentSent.getString("text").equals("")) {
            // put this sentence into the new partition
            tabularData.put(currentSent);
        }
        JSONObject res = new JSONObject();
    res.put("coreData", tabularData);
    res.put("locationFreq", locFreqMap);
    System.out.println(res);
    return res;
    }

    public JSONArray parseTime(JSONArray dataToParse) {
        return dataToParse;
    }

    public JSONArray parseLocation(JSONArray dataToParse) {
        return dataToParse;
    }

    public List<CoreSentence> splitSents(String article) {
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize, ssplit");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument doc = new CoreDocument(article);
        // annotate
        pipeline.annotate(doc);
        // display sentences
        return doc.sentences();
    }

    private class NERDate implements Comparable<NERDate>{
        private final String dateVal;
        public NERDate(String dateVal) {
            this.dateVal = dateVal;
        }
        public String getDateVal() {
            return this.dateVal;
        }
        @Override
        public int compareTo(NERDate o) {
            return this.dateVal.length() - o.getDateVal().length();
        }
        @Override
        public String toString() {
            return this.dateVal;
        }
    }
    private class NERLoc implements Comparable<NERLoc>{
        private final String locType;
        private final String locVal;
        public NERLoc(String locType, String locVal) {
            this.locVal = locVal;
            this.locType = locType;
        }
        public String getLocVal() {
            return this.locVal;
        }
        public String getLocType() {
            return this.locType;
        }
        @Override
        public int compareTo(NERLoc o) {
            Map<String, Integer> locTypeOrder = new HashMap<String, Integer>();
            locTypeOrder.put("CITY", 3);
            locTypeOrder.put("STATE_OR_PROVINCE", 2);
            locTypeOrder.put("COUNTRY", 1);
            return locTypeOrder.get(this.locType) - locTypeOrder.get(o.getLocType());
        }
        @Override
        public String toString() {
            return this.locType + ": " + this.locVal;
        }


    }
}
