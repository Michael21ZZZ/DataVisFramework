package edu.cmu.cs214.hw6.NLP;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class DateReg {
    /**
     * return regrexed date if possible else ""
     * @param dateStr
     * @param prevDateStr
     * @return
     */
    public static String dateReg(String dateStr, String prevDateStr) {
        if (dateStr.equals("PAST_REF")) {
            return prevDateStr;
        }
        String prevYear = prevDateStr.substring(0, 4);
        String prevMon = prevDateStr.substring(5, 7);
        String prevDay = prevDateStr.substring(8, 10);
        int dateLen = dateStr.length();
        String year = "";
        String month = "";
        String day = "";
        if (dateLen == 10) {
            year = dateStr.substring(0, 4);
            month = dateStr.substring(5, 7);
            day = dateStr.substring(8, 10);
        } else if (dateLen == 7) {
            year = dateStr.substring(0, 4);
            month = dateStr.substring(5, 7);
        } else if (dateLen == 4) {
            year = dateStr.substring(0, 4);
        }
        // year
        if (year.equals("XXXX")) {
            year = prevYear;
        }
        // month
        if (month.equals("")) {
            month = prevMon;
        } else { // season
            month = seasonToMon(month);
        }
        // day 
        if (day.equals("")) {
            day = prevDay;
        }
        return year + "-" + month + "-" + day;
    }

    public static String seasonToMon(String season) {
        Map<String, String> seaToMon = new HashMap<String, String>();
        seaToMon.put("SU", "07");
        seaToMon.put("SP", "01");
        seaToMon.put("FA", "09");
        seaToMon.put("WI", "10");
        return seaToMon.getOrDefault(season, "01");
    }
}
