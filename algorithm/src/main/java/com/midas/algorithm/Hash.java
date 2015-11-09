package com.midas.algorithm;

import java.util.HashMap;


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
}
