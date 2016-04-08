package midas.algorithm;

import java.util.HashMap;
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
}
