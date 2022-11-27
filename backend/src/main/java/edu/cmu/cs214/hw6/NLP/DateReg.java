package edu.cmu.cs214.hw6.NLP;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class DateReg {
    public static String patternCentury = "[1-9]{2}XX"; // 18XX
    public static String patternYearPast = "P[1-9]{2}Y"; // P18Y
    public static String patternDecade = "[1-9]{3}X"; // 198X
    /**
     * return regrexed date if possible else ""
     * @param dateStr
     * @param prevDateStr
     * @return
     */
    public static String dateReg(String dateStr, String prevDateStr) {
        if (dateStr == null) {
            return prevDateStr;
        }
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
        } else if (Pattern.matches(DateReg.patternCentury, year)) { //18XX
            year = year.substring(0, 2) + "00";
        } else if (Pattern.matches(DateReg.patternDecade, year)) { // 198X
            year = year.substring(0, 3) + "0";
        } else if (Pattern.matches(DateReg.patternYearPast, year)) { // P18Y
            int addYears = Integer.parseInt(year.substring(1, 3));
            int newYear = Integer.parseInt(prevYear) + addYears;
            year = Integer.toString(newYear);
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
