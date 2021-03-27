package com.anupam.frameworkSetup.API;/*
Generating random sub list of items (without duplicates) from given list of items.
Input : items = {item1, item2, ... , itemn}
Output 1 : {}
Output 2 : {item1, item2}
Output 3 : {item1, item2, ... , itemn}
*/


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class soulution{

    // Returns single List
    public List<String> returnRandomList(List<String> mainlist){
        Random rand = new Random();
        int listSize = rand.nextInt(mainlist.size());
        List<String> randomList = new ArrayList<String>();
        for(int i=0; i<listSize; i++){
            String value = mainlist.get(rand.nextInt(mainlist.size()));
            if(randomList.contains(value))
                value = mainlist.get(rand.nextInt(mainlist.size()));
            randomList.add(value);
        }
        return randomList;
    }

    // Returns Multiple List
    public static List<List<Object>> returnRandomList(List<String> mainlist, int numberOfList){
        Random rand = new Random();
        int listSize = rand.nextInt(mainlist.size());
        List<List<Object>> superList = new ArrayList<>();
        List<String> randomList = new ArrayList<String>();
        for(int j=0;j<=numberOfList;j++){
            for(int i=0; i<listSize; i++){
                String value = mainlist.get(rand.nextInt(mainlist.size()));
                if(randomList.contains(value))
                    value = mainlist.get(rand.nextInt(mainlist.size()));
                randomList.add(value);
            }
            superList.add(randomList.stream().distinct().collect(Collectors.toList()));
        }

        return superList;
    }

    public static void main(String[] args){
        List<String> itemList = new ArrayList<>();
        itemList.add("apple");
        itemList.add("mango");
        itemList.add("sugar");
        itemList.add("salt");
        itemList.add("pepper");
        itemList.add("pepperboat");
        itemList.add("juice");
        itemList.add("gum");
        List<List<Object>> finalListOfList = returnRandomList(itemList, 4);
        System.out.println(finalListOfList.toString());
    }

}


