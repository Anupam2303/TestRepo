package com.anupam.tests;

// logs1,logs2,logs3,log4,log5,log6,log7,log8,loh9,log10

// n number iteration , n testcase n steps for every case

// Iter : 10 , Test : 10 , Step 10  :
// ***** Total : 200

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class inmobitest {

//    class Steps{
//        int numberOfsteps;
//
//    }
//     class test {
//         List<Steps> allSteps;
//     }

     public static int getNumberofFiles(String dirpath){
        File directory = new  File(dirpath);
         return directory.list().length;
     }

     public static String readfileall(String dirpath,String filename) throws IOException {
         BufferedReader reader = new BufferedReader(new FileReader(dirpath+filename));
         StringBuilder sb = new StringBuilder();
         String line = "";
         String ls = System.getProperty("line.seprate");
         while ((line = reader.readLine())!=null)
         {
             sb.append(ls);
         }
         reader.close();
         return sb.toString();
     }

     public static String getFinalcontent(String fileContent){
         int length = fileContent.length();
        return fileContent.substring((length-10),length);
     }

    public static HashMap<String,String> getFilenameStepCount(String dirpath) throws IOException {
        File directory = new  File(dirpath);
        String[] filelist = directory.list();
        HashMap<String,String> filenameTestCount = new HashMap<>();
        for (String fileName : filelist) {
            String filecontent = readfileall(dirpath, fileName);
            String finalContent = getFinalcontent(filecontent);
            filenameTestCount.put(fileName, finalContent);
        }
        return filenameTestCount;
    }

    public static void main(String[] args) {

    }


}
