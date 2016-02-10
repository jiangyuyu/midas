package com.midas.algorithm;

/**
 * Created by xli1 on 11/6/15.
 */
public class DP {
  public String longestPalindrome(String s) {
    if (s == null) return s;
    int N = s.length();
    if (N <= 1) return s;
    char[] arr = new char[2 * N + 1];
    for (int i = 0; i < N; i++) {
      arr[i * 2] = '#';
      arr[i * 2 + 1] = s.charAt(i);
    }
    arr[N * 2] = '#';
    int[] p = new int[2 * N + 1];  //save the length of the palindrome centers at arr[i]
    int maxlen = 0, maxcenter = 0;
    int center = 0, right = 0;
    for (int i = 1; i <= 2 * N; i++) {
      int mirror = 2 * center - i;
      if (mirror >= 0 && i + p[mirror] < right) p[i] = p[mirror];
      else {
        p[i] = right > i ? right-i : 0;
        while (i-p[i]-1 >= 0 && i + p[i] + 1 <= 2 * N && arr[i-p[i]-1] == arr[i+p[i]+1]) p[i]++;
        if (i + p[i] > right) {
          center = i;
          right = i + p[i];
          if (p[i] > maxlen) {
            maxlen = p[i];
            maxcenter = center;
          }
        }
      }
    }
    return s.substring((maxcenter - maxlen)/2, (maxcenter+maxlen)/2);
  }
}
