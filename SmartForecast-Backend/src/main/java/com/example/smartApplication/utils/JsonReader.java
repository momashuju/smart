package com.example.smartApplication.utils;

import com.alibaba.fastjson.JSON;
import java.io.*;


public class JsonReader {

    public static void main(String[] args){

    }



    public static Object ReadFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader= new BufferedReader(isr);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return JSON.parse(laststr);
    }

}
