package midas.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
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

  public int[] intersectSort(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int i = 0, j = 0, k = 0;
    int[] ret = new int[Math.min(nums1.length, nums2.length)];
    while (i < nums1.length && j < nums2.length) {
      if (nums1[i] < nums2[j]) {
        i++;
      } else if (nums1[i] > nums2[j]) {
        j++;
      } else {
        ret[k++] = nums1[i];
        i++;
        while (i < nums1.length && nums1[i] == nums1[i-1]) i++;
        j++;
        while (j < nums2.length && nums2[j] == nums2[j-1]) j++;
      }
    }
    return Arrays.copyOfRange(ret, 0, k);
  }

  public int[] intersectSortII(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int i = 0, j = 0, k = 0;
    int[] ret = new int[Math.min(nums1.length, nums2.length)];
    while (i < nums1.length && j < nums2.length) {
      if (nums1[i] < nums2[j]) {
        i++;
      } else if (nums1[i] > nums2[j]) {
        j++;
      } else {
        ret[k++] = nums1[i];
        i++;
        j++;
      }
    }
    return Arrays.copyOfRange(ret, 0, k);
  }
}
