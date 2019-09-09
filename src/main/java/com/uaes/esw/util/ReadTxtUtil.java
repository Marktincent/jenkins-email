package com.uaes.esw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * @author xu wen feng 10/10/2018 14:24
 */
public class ReadTxtUtil {


    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
