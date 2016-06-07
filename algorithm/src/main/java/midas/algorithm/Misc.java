package midas.algorithm;

public class Misc {
  public boolean isSelfCrossing(int[] x) {
    if (x == null) {
      throw new IllegalArgumentException();
    }
    int N = x.length;
    if (N <= 3) return false;
    for (int i = 3; i < N; i++) {
      // case 1:
      if (x[i] >= x[i-2] && x[i-1] <= x[i-3]) return true;
      // case 2:
      if(i>=4 && x[i-4]+x[i]>=x[i-2] && x[i-3]==x[i-1]) return true;
      // case 3:
      if (i >= 5 && x[i-4] < x[i-2] && x[i] + x[i-4] >= x[i-2] && x[i-1] < x[i-3] && x[i-1] + x[i-5] >= x[i-3]) return true;
    }
    return false;
  }
}
