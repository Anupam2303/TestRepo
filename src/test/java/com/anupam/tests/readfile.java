package com.anupam.tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class readfile {

    public static void main(String[] args) throws IOException {
        FileReader rd = new FileReader("dummyanupam");
        int i;
        while((i=rd.read())!=-1){
//            char c= (char)(rd.read());
            System.out.print((char)i);
        }
        rd.close();

//        FileWriter fileWriter = new FileWriter()
    }
}
