package midas.algorithm;

public class BitOps {
  public int singleNumber(int[] A) {
    // Write your code here
    if (A == null || A.length == 0) {
      return 0;
    }
    int single = A[0];
    for (int i = 1; i < A.length; i++) {
      single = single ^ A[i];
    }
    return single;
  }

  public boolean isPowerOfFour(int num) {
    return num > 0 && (num & (num-1)) == 0 &&  (num & 0x55555555) > 0;
  }

  // naive solution
  public int[] countBitsNaive(int num) {
    if (num < 0) {
      return null;
    }
    int[] ret = new int[num + 1];
    for (int i = 0; i <= num; i++) {
      int count = 0;
      int temp = i;
      while (temp > 0) {
        if ((temp & 1) > 0) count++;
        temp = temp >> 1;
      }
      ret[i] = count;
    }
    return ret;
  }

  public int[] countBitsPattern(int num) {
    if (num < 0) {
      return null;
    }
    int[] ret = new int[num + 1];
    if (num == 0) return ret;
    if (num >= 1) {
      ret[0] = 0;
      ret[1] = 1;
    }
    int cur = 2, step = 1;
    while (cur <= num) {
      for (int j = 0; j < step && cur + j <= num; j++) {
        ret[cur + j] = ret[cur - step + j];
      }
      for (int j = 0; j < step && cur + step + j <= num; j++) {
        ret[cur + step + j] = ret[cur - step + j] + 1;
      }
      cur += step * 2;
      step = step * 2;
    }
    return ret;
  }

  public int[] countBitsSmartPattern(int num) {
    if (num < 0) return null;
    int[] ret = new int[num + 1];
    ret[0] = 0;
    if (num >= 1) ret[1] = 1;
    for (int i = 2; i <= num; i++) {
      if (i%2 == 0) ret[i] = ret[i/2];
      else ret[i] = ret[i/2] + 1;
    }
    return ret;
  }



  public static void main(String[] args) {
    BitOps test = new BitOps();
    int[] ret = test.countBitsPattern(15);
    for (int i : ret) {
      System.out.println(i);
    }
  }
}
