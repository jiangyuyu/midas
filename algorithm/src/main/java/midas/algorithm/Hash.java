package midas.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xli1 on 11/6/15.
 */
public class Hash {
  public int[] twoSum(int[] nums, int target) {
    if (nums == null || nums.length < 2) return null;
    HashMap<Integer, Integer> valueWithIndex = new HashMap<Integer, Integer>();
    int[] res = null;
    for (int i = 0; i < nums.length; i++) {
      int remain = target - nums[i];
      if (valueWithIndex!=null && valueWithIndex.containsKey(remain)){
        res = new int[2];
        res[0] = valueWithIndex.get(remain);
        res[1] = i+1;
        return res;
      } else {
        valueWithIndex.put(nums[i], i+1);
      }
    }
    return res;
  }

  public int shortestDistanceLinear(String[] words, String word1, String word2) {
    if (words == null || words.length == 0) return -1;
    int pos1 = -1, pos2 = -1, minDist = Integer.MAX_VALUE;
    if (word1.equals(word2)) return 0;
    for (int i = 0; i < words.length; i++) {
      if (words[i].equals(word1)) {
        pos1 = i;
        if (pos2 != -1) minDist = Math.min(Math.abs(pos2 - pos1), minDist);
      } else if (words[i].equals(word2)) {
        pos2 = i;
        if (pos1 != -1) minDist = Math.min(Math.abs(pos2 - pos1), minDist);
      }
    }
    return minDist;
  }

  // Use HashMap to index the words
  public int shortestDistance(String[] words, String word1, String word2) {
    if (words == null || words.length == 0) return -1;
    Map<String, List<Integer>> index = new HashMap<String, List<Integer>>();
    for (int i = 0; i < words.length; i++) {
      if (!index.containsKey(words[i])) {
        List<Integer> l = new ArrayList<Integer>();
        index.put(words[i], l);
      }
      index.get(words[i]).add(i);
    }
    if (!index.containsKey(word1) || !index.containsKey(word2)) return -1;
    if (word1.equals(word2)) return 0;
    List<Integer> l1 = index.get(word1);
    List<Integer> l2 = index.get(word2);
    int minDistance = Integer.MAX_VALUE;
    int i = 0, j = 0;
    while (i < l1.size() && j < l2.size()) {
      int curDistance = Math.abs(l1.get(i) - l2.get(j));
      if (curDistance< minDistance) {
        minDistance = curDistance;
      }
      if (l1.get(i) < l2.get(j)) {
        i++;
      } else {
        j++;
      }
    }
    return minDistance;
  }

  public int shortestDistanceConsiderSameWords(String[] words, String word1, String word2) {
    if (words == null || words.length == 0) return -1;
    Map<String, List<Integer>> index = new HashMap<String, List<Integer>>();
    for (int i = 0; i < words.length; i++) {
      if (!index.containsKey(words[i])) {
        List<Integer> l = new ArrayList<Integer>();
        index.put(words[i], l);
      }
      index.get(words[i]).add(i);
    }
    if (!index.containsKey(word1) || !index.containsKey(word2)) return -1;

    List<Integer> l1 = index.get(word1);
    List<Integer> l2 = index.get(word2);
    int minDistance = Integer.MAX_VALUE;
    int i = 0, j = 0;
    while (i < l1.size() && j < l2.size()) {
      if (word1.equals(word2) && l1.get(i) == l2.get(j)) {
        i++;
        continue;
      }
      int curDistance = Math.abs(l1.get(i) - l2.get(j));
      if (curDistance< minDistance) {
        minDistance = curDistance;
      }
      if (l1.get(i) < l2.get(j)) {
        i++;
      } else {
        j++;
      }
    }
    return minDistance;
  }

  public static void main(String[] args) {
    Hash test = new Hash();
    String[] words = {"a", "b"};
    System.out.println(test.shortestDistance(words, "a", "b"));
  }
}
