package edu.cmu.cs214.hw6.NLP;

public class Test {
    public static void main(String[] args) {
        String[] dateTests = {"2021-06-04", "1862-SU", "XXXX-FA", "1864", "XXXX-03", "PAST_REF", "XXXX-06-02"};
        for (String date: dateTests) {
            System.out.println(DateReg.dateReg(date, "2000-77-99"));
        }
        
    }
}
