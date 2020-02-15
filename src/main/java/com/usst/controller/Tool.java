package com.usst.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tool {
  static Map<String, List<String>> resMap=new HashMap<String, List<String>>();
  static Set<String> stopWords=new HashSet<String>();
  public void upload(String path) throws IOException {
    if (stopWords.size()==0){
      FileInputStream inputStream = new FileInputStream("C:\\Users\\DELL\\IdeaProjects\\pdp_git\\PDP_5\\SpringMvc1\\src\\main\\java\\com\\usst\\cfc\\stopword");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String str = null;
      while((str = bufferedReader.readLine()) != null){
        System.out.println(str);
        stopWords.add(str.trim());
      }
    }
    String curId="";
    Map<String,List<String>> map=new HashMap<String, List<String>>();
    FileInputStream inputStream = new FileInputStream(path);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    String str = null;
    while((str = bufferedReader.readLine()) != null)
    {

      if (str.length()>=2&&str.substring(0,2).equals("PN")){
        curId=str.substring(3);
      }

      String[] strs=str.split(" ");
      for (String cur:strs){
        if (stopWords.contains(cur))continue;
        if (!map.containsKey(cur)){
          map.put(cur,new ArrayList<String>());
        }
        map.get(cur).add(curId);
      }

    }

    //close
    inputStream.close();
    bufferedReader.close();
    resMap=Merge(map,resMap);
  }

  public Map<String,List<String>> Merge(Map<String,List<String>> map1,Map<String,List<String>> map2){
    Map<String,List<String>> newMap=new HashMap<String, List<String>>();
    for(String key:map1.keySet()){
      List<String> temp=map1.get(key);
      if (map2.containsKey(key)){
        temp.addAll(map2.get(key));
        map2.remove(key);
      }
      newMap.put(key,temp);
    }
    newMap.putAll(map2);
    return newMap;
  }

}
