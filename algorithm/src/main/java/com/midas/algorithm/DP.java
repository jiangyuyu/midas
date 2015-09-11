package com.midas.algorithm;

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

  // too much time and space
  public int numSquares(int n) {
    int[][] f = new int[n+1][n+1];

    for (int i = 1; i <= n; i++) {
      f[1][i] = i;
    }
    for (int i = 2; i <= n; i++) {
      for (int j = i; j <= n; j++) {
        if (j > i * i) {
          f[i][j] = Math.min(f[i - 1][j], 1 + f[i][j - i * i]);
        } else if (j == i * i) {
          f[i][j] = 1;
        } else {
          f[i][j] = f[i-1][j];
        }
      }
    }

    return f[n][n];
  }
  public int numSquares2(int n) {
    if (n == 0) return 0;
    int[] f = new int[n+1];
    for (int i = 0; i * i <= n; i++) {
      f[i*i] =  1;
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; i + j * j <=n; j++) {
        if (f[i + j*j] == 0 || f[i + j*j] > f[i] + 1) {
          f[i + j*j] = f[i] + 1;
        }
      }
    }
    return f[n];
  }

  public int numWaysPainting(int n, int k) {
    if (n <= 1 || k == 0) return n * k;
    int same = 0, diff = k;
    for (int i = 1; i < n; i++) {
      int curSame = diff;
      int curDiff = (k-1)*(same + diff);
      same = curSame;
      diff = curDiff;
    }
    return same + diff;
  }

  public static void main(String[] args) {
    int[] array = {5, 4, 5, 11, 5};
    DP dp = new DP();
    System.out.println(dp.numWaysPainting(4, 2));

  }
}
