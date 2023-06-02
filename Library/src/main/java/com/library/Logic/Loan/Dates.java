package com.library.Logic.Loan;

import java.sql.Date;
import java.util.Calendar;

public class Dates {

    public static Date GetCurrentDate(){
        return new java.sql.Date(new java.util.Date().getTime());
    }

    public static Date GetDueDate(){
        java.util.Date date = new java.util.Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DATE, 7);

        return new java.sql.Date(c.getTime().getTime());
    }
}
