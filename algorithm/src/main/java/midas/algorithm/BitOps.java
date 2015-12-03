package midas.algorithm;

/**
 * Created by xli1 on 10/29/15.
 */
public class BitOps {
  public int aplusb(int a, int b) {
    if (a < 0 || b < 0) return 0;
    while (b != 0) {
      int carry = a & b;
      a = a ^ b;
      b = carry << 1;
    }
    return a;
  }

  public int aplusbRec(int a, int b) {
    if (a < 0 || b < 0) return 0;
    if (b == 0) return a;
    return aplusbRec(a^b, (a&b)<<1);
  }

  public String dec2bin (int i) {
    StringBuilder sb = new StringBuilder();
    int x = i;
    while (x != 0) {
      if ((x & 1) == 1) {
        sb.insert(0, "1");
      } else {
        sb.insert(0, "0");
      }
      x = x >> 1;
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    BitOps test = new BitOps();
    System.out.println(test.dec2bin(-1));
  }
}
