package midas.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Hash {
  public int[] twoSum(int[] nums, int target) {
    if (nums == null || nums.length < 2) {
      return null;
    }
    Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
    for (int i  = 0; i < nums.length; i++) {
      if (temp.containsKey(target - nums[i])) {
        int[] index = new int[2];
        index[0] = temp.get(target - nums[i]);
        index[1] = i;
        return index;
      } else {
        temp.put(nums[i], i);
      }
    }
    return null;
  }

  public int[] intersection(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    HashSet<Integer> temp = new HashSet<Integer>();
    for (int i : nums1) {
      temp.add(i);
    }
    HashSet<Integer> intersect = new HashSet<Integer>();
    for (int i : nums2) {
      if (temp.contains(i)) {
        intersect.add(i);
      }
    }
    int[] ret = new int[intersect.size()];
    int idx = 0;
    for (int i : intersect) {
      ret[idx++] = i;
    }
    return ret;
  }

  public int[] intersectII(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    HashMap<Integer, Integer> valueFreq = new HashMap<Integer, Integer>();
    for (int n : nums1) {
      if (!valueFreq.containsKey(n)) {
        valueFreq.put(n, 1);
      } else {
        valueFreq.put(n, valueFreq.get(n) + 1);
      }
    }
    ArrayList<Integer> intersect = new ArrayList<Integer>();
    for (int n : nums2) {
      if (valueFreq.containsKey(n)) {
        intersect.add(n);
        if (valueFreq.get(n) == 1) {
          valueFreq.remove(n);
        } else {
          valueFreq.put(n, valueFreq.get(n) -1);
        }
      }
    }
    int[] ret = new int[intersect.size()];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = intersect.get(i).intValue();
    }
    return ret;
  }
}
