package com.midas.algorithm;

import java.util.Arrays;


/**
 * Created by xli1 on 11/6/15.
 */
public class TwoPointers {
  // Assume the array is sorted.
  public int[] twoSumSorted(int[] nums, int target) {
    if (nums == null || nums.length < 2) return null;
    int left = 0, right = nums.length - 1;
    int[] res = null;
    while (left != right) {
      int sum = nums[left] + nums[right];
      if (sum == target) {
        res = new int[2];
        res[0] = left + 1;
        res[1] = right + 1;
      } else if (sum < target) {
        left++;
      } else {
        right++;
      }
    }
    return res;
  }

  public int lengthOfLongestSubstring(String s) {
    int maxlen = 0;
    if (s == null || s.length() == 0) return maxlen;

    int[] lastIndex = new int[256];
    Arrays.fill(lastIndex, -1);
    int left = 0;
    for (int right = 0; right < s.length(); right++) {
      char curChar = s.charAt(right);
      if (lastIndex[curChar] == -1) {
        lastIndex[curChar] = right;
      } else {
        maxlen = Math.max(maxlen, right-left);
        while (left <= lastIndex[curChar]) {
          lastIndex[s.charAt(left)] = -1;
          left++;
        }
        lastIndex[curChar] = right;
      }
    }
    maxlen = Math.max(maxlen, s.length()-left);
    return maxlen;
  }

  public static void main(String[] args) {
    TwoPointers test = new TwoPointers();
    System.out.println(test.lengthOfLongestSubstring("abcabcbb"));
  }
}
