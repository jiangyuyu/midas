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
}
