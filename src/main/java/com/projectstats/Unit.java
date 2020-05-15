package com.projectstats;

import java.text.DecimalFormat;

class Unit {
    static String getSize(Long bytes) {
        String resultI = calculateSize(bytes, 1024.0, 1048576.0, 1073741824.0, 1099511627776.0, "i");
        String result = calculateSize(bytes, 1000.0, 1000000.0, 1000000000.0, 1000000000000.0, "");
        return resultI + " / " + result;
    }

    static String calculateSize(long bytes, double unK, double unM, double unG, double unT, String iOrNot) {

        double kilobyte = bytes / unK;
        double megabyte = bytes / unM;
        double gigabyte = bytes / unG;
        double terabyte = bytes / unT;

        DecimalFormat dec = new DecimalFormat("0.00");
        if (terabyte > 1) {
            return dec.format(terabyte).concat(" T").concat(iOrNot).concat("B");
        } else if (gigabyte > 1) {
            return dec.format(gigabyte).concat(" G").concat(iOrNot).concat("B");
        } else if (megabyte > 1) {
            return dec.format(megabyte).concat(" M").concat(iOrNot).concat("B");
        } else if (kilobyte > 1) {
            return dec.format(kilobyte).concat(" K").concat(iOrNot).concat("B");
        } else {
            return dec.format(bytes).concat(" B");
        }
    }
}
