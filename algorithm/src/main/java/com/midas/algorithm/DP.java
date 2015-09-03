package com.midas.algorithm;

import java.util.HashSet;


/**
 * Created by xli1 on 4/9/15.
 */
public class DP {
  public int bestThief(int[] array) {
    if (array == null || array.length == 0) return 0;
    int maxProfit = 0;
    int N = array.length;
    int[] f = new int[N];
    if (N > 0) f[0] = array[0];
    if (N > 1) f[1] = array[1];
    if (N > 2) f[2] = f[0] + array[2];

    if (N == 1) return f[0];
    if (N == 2) return f[0] > f[1]? f[0]:f[1];
    if (N > 2)
      maxProfit = Math.max(f[0], Math.max(f[1], f[2]));
    for (int i = 3; i < N; i++) {
      f[i] = Math.max(array[i], Math.max(f[i-2]+array[i], f[i-3]+array[i]));
      if (f[i] > maxProfit) maxProfit = f[i];
    }
    return maxProfit;
  }

  public static void main(String[] args) {
    int[] array = {5, 4, 5, 11, 5};
    DP dp = new DP();
    System.out.println(dp.bestThief(array));

    HashSet<Character.UnicodeScript> set = new HashSet<Character.UnicodeScript>();
    for (Character.UnicodeScript s : Character.UnicodeScript.values()) set.add(s);
    System.out.println(set.size() + "\n");
  }
}
