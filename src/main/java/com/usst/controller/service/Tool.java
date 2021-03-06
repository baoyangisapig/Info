package com.usst.controller.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Tool class provides the method to construct the inverted indexes based on the input files.
 */
public class Tool {

  public static Map<String, List<String>> resMap = new HashMap<String, List<String>>();// the map used to store the whole inverted indexes.
  static Set<String> stopWords = new HashSet<String>();// the map used to store all stop words.

  /**
   * parse the input file and construct the inverted indexes data structure.
   *
   * @param path the path of the file.
   * @throws IOException
   */
  public void upload(String path) throws IOException {
    if (stopWords.size() == 0) {
      FileInputStream inputStream = new FileInputStream("C:\\Users\\DELL\\IdeaProjects\\pdp_git\\PDP_5\\SpringMvc1\\src\\main\\java\\com\\usst\\cfc\\stopWords.txt");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        System.out.println(str);
        stopWords.add(str.trim());
      }
    }
    String curId = "";
    Map<String, List<String>> map = new HashMap<String, List<String>>();

    FileInputStream inputStream = new FileInputStream(path);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    String str = null;
    //get and update the current file number
    while ((str = bufferedReader.readLine()) != null) {
      if (str.length() >= 2 && str.substring(0, 2).equals("RN")) {
        curId = str.substring(3);
      }
      String[] strs = str.split("\\s+");
      // add each word in the file into the inverted indexes/
      for (String cur : strs) {
        cur = format(cur);
        if (cur.equals("")) continue;
        if (stopWords.contains(cur)) continue;
        if (!map.containsKey(cur)) {
          map.put(cur, new ArrayList<String>());
        } else {
          if (!map.get(cur).contains(curId))
            map.get(cur).add(curId);
        }
      }
    }
    inputStream.close();
    bufferedReader.close();
    resMap = Merge(map, resMap);
  }

  /**
   * The method is used to merge the inverted indexes of the current file to the whole inverted
   * indexes structure.
   *
   * @param map1 inverted indexes of the current file.
   * @param map2 the whole inverted indexes structure.
   * @return merged inverted indexes.
   */
  public Map<String, List<String>> Merge(Map<String, List<String>> map1, Map<String, List<String>> map2) {
    Map<String, List<String>> newMap = new HashMap<String, List<String>>();
    for (String key : map1.keySet()) {
      List<String> temp = map1.get(key);
      if (map2.containsKey(key)) {
        temp.addAll(map2.get(key));
        map2.remove(key);
      }
      newMap.put(key, temp);
    }
    newMap.putAll(map2);
    return newMap;
  }

  /**
   * parse and formalize the token.
   *
   * @param token the token that needs to be formalized.
   * @return formalized token.
   */
  public static String format(String token) {
    int low = 0;
    int high = token.length() - 1;
    while (low <= high && !Character.isLetter(token.charAt(low))) {
      low++;
    }
    while (low <= high && !Character.isLetter(token.charAt(high))) {
      high--;
    }
    if (low > high) return "";
    else return token.substring(low, high + 1).toLowerCase();
  }

}
