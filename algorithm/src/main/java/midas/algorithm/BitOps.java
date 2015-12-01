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
    int x = i;
    StringBuilder sb = new StringBuilder();
    if (i < 0) {
      if (i == Integer.MIN_VALUE) {
        sb.append("1");
        for (int j = 0; j < 31; j++) {
          sb.append("0");
        }
        return sb.toString();
      } else {
        x = -i;
      }
    }

    if (i < 0) {
      x = x-1;
    }

    while (x != 0) {
      if ((x & 1) == 1) {
        sb.insert(0, "1");
      } else {
        sb.insert(0, "0");
      }
      x = x >> 1;
    }

    if (i < 0) {
      for (int j = 0; j < sb.length(); j++) {
        if (sb.charAt(j) == '1') {
          sb.setCharAt(j, '0');
        } else {
          sb.setCharAt(j, '1');
        }
      }
    }

    return sb.toString();
  }

  public static void main(String[] args) {
    BitOps test = new BitOps();
    System.out.println(test.dec2bin(-1));
  }
}
