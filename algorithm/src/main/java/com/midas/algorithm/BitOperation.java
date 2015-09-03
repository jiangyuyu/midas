package com.midas.algorithm;

/**
 * Created by xli1 on 8/17/15.
 */
public class BitOperation {
  public int singleNumber(int[] nums) throws Exception {
    if (nums == null || nums.length < 1)
      throw new IllegalArgumentException();
    int xor = 0;
    for (int i= 0; i < nums.length; i++) {
      xor ^= nums[i];
    }
    return xor;
  }

  public int singleNumberThree(int[] nums) throws Exception {
    if (nums == null || nums.length == 0)
      throw new IllegalArgumentException();
    int res = 0;
    for (int i = 0; i < 32; i++) {
      int test = 1 << i;
      int count = 0;
      for (int j = 0; j < nums.length; j++) {
        if ((nums[j] & test) != 0) count++;
      }
      if (count % 3 != 0) res = res | test;
    }
    return res;
  }

  public int[] twoSingleNumber(int[] array) {
    if (array == null || array.length < 2 || array.length % 2 != 0)
      return null;
    int xor = array[0];        // 0 ^ anything = anything
    for (int i = 1; i < array.length; i++) {
      xor = xor ^ array[i];
    }
    int rightmost_setbit = xor & ~(xor-1); // find the rightmost set bit of xor
    int[] result = new int[2];
    for (int i = 0; i < array.length; i++) {
      if ((array[i] & rightmost_setbit) == 0) {
        result[0] = result[0] ^ array[i];
      } else {
        result[1] = result[1] ^ array[i];
      }
    }
    return result;
  }

  public static void main(String[] args) {

  }
}
