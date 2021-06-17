package com.anupam.tests;

import java.util.ArrayList;
import java.util.List;

/*
*
* {lion, tiger, buffalo, goat}


  +
  +
 ++
++++
++++
++++
++++

a[7][7]

 7 , 4 (mxlen- surrentStringlen >=0)
 -



a7 1 - +

*
* */
public class Xyz {

    public static int maxlengthStr(List<String> listString){
        ///Login
        return 7;

    }

    public static void main(String[] args) {
        List<String> listString = new ArrayList<>();
        listString.add("lion");
        listString.add("buffalo");
        listString.add("tiger");
        listString.add("goat");

        int maclenth = maxlengthStr(listString);
        int index=0;
        for (int i=maclenth;i>0;i--){
            index=0;
            for (int j=0;j<listString.size();j++){
                if ((i-listString.get(index).length())>0)
                    System.out.print(" ");
                else
                    System.out.print("+");
                index++;
            }
            System.out.println();
        }

    }
}
