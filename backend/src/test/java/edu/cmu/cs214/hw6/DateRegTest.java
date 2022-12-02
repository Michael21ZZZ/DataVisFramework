package edu.cmu.cs214.hw6;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.cmu.cs214.hw6.NLP.DateReg;

public class DateRegTest {
    @Test
    public void DateRegTest1() {
        String prevDate = "2000-77-99";
        assertEquals("2021-06-04", DateReg.dateReg("2021-06-04", prevDate)); 
        assertEquals("1862-07-99", DateReg.dateReg("1862-SU", prevDate)); 
        assertEquals("2000-09-99", DateReg.dateReg("XXXX-FA", prevDate)); 
        assertEquals("1864-77-99", DateReg.dateReg("1864", prevDate)); 
        assertEquals("2000-03-99", DateReg.dateReg("XXXX-03", prevDate)); 
        assertEquals("2000-77-99", DateReg.dateReg("PAST_REF", prevDate)); 
        assertEquals("2000-06-02", DateReg.dateReg("XXXX-06-02", prevDate)); 
        assertEquals("2021-06-04", DateReg.dateReg("2021-06-04", prevDate)); 
        assertEquals("2021-06-04", DateReg.dateReg("2021-06-04", prevDate)); 
    }    
}
