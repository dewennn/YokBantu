package com.application.yokbantu;

import java.text.DecimalFormat;

public class StaticFunctions {

    public static String formatCashRp(int num){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return("Rp. " + formatter.format(num));
    }

}
