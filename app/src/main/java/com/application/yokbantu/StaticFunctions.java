package com.application.yokbantu;

import java.text.NumberFormat;
import java.util.Locale;

public class StaticFunctions {

    public static String formatCashRp(Integer input){
        return(NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(input).replace(",00", ""));
    }

}
