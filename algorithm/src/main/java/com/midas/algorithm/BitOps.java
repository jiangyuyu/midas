package com.midas.algorithm;

/**
 * Created by xli1 on 10/29/15.
 */
public class BitOps {
  public int aplusb(int a, int b) {
    if (a < 0 || b < 0) return 0;
    while (b != 0) {
      int carry = a & b;
      a = a ^b;
      b = carry << 1;
    }
    return a;
  }

  public int aplusbRec(int a, int b) {
    if (a < 0 || b < 0) return 0;
    if (b == 0) return a;
    return aplusbRec(a^b, (a&b)<<1);
  }


}
