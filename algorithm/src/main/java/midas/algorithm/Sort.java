package midas.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class Sort {
  public List<Integer> topKFrequent(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k < 1) {
      return null;
    }
    Map<Integer, Integer> fTable = new HashMap<Integer, Integer>();
    for (int n : nums) {
      if (fTable.containsKey(n)) {
        fTable.put(n, fTable.get(n) + 1);
      } else {
        fTable.put(n, 1);
      }
    }
    PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<Map.Entry<Integer, Integer>>(k,
        new Comparator<Map.Entry<Integer, Integer>>() {
          public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
            return a.getValue().compareTo(b.getValue());
          }
        });
    for (Map.Entry<Integer, Integer> entry : fTable.entrySet()) {
      if (pq.size() < k) {
        pq.add(entry);
      } else {
        if (entry.getValue() > pq.peek().getValue()) {
          pq.poll();
          pq.add(entry);
        }
      }
    }
    List<Integer> ret = new ArrayList<Integer>();
    while (!pq.isEmpty()) {
      ret.add(pq.poll().getKey());
    }
    return ret;
  }

  public static void main(String[] args) {
  }
}
