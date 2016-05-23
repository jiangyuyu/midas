package midas.algorithm;

import java.util.HashMap;

public class MathAlgo {
  private HashMap<Integer, String> romanMap = new HashMap<Integer, String>();
  private HashMap<Character, Integer> intMap = new HashMap<Character, Integer>();
  private void fillRomanMap() {
    romanMap.put(1, "I");
    romanMap.put(5,	"V");
    romanMap.put(10, "X");
    romanMap.put(50, "L");
    romanMap.put(100, "C");
    romanMap.put(500, "D");
    romanMap.put(1000, "M");
  }
  private void fillIntMap() {
    intMap.put('I', 1);
    intMap.put('V', 5);
    intMap.put('X', 10);
    intMap.put('L', 50);
    intMap.put('C', 100);
    intMap.put('D', 500);
    intMap.put('M', 1000);
  }
  public int romanToInt(String s) {
    fillIntMap();
    if (s == null || s.length() <= 0) {
      return 0;
    }
    int total = 0, pos = 0;
    while (pos < s.length()) {
      char cur = s.charAt(pos);
      char next = pos < s.length() - 1? s.charAt(pos + 1) : 0;
      if (next != 0 && intMap.get(cur) < intMap.get(next)) {
        total += intMap.get(next) - intMap.get(cur);
        pos += 2;
      } else {
        total += intMap.get(cur);
        pos++;
      }
    }
    return total;
  }

  public String intToRoman(int num) {
    fillRomanMap();
    if (num < 1 || num > 3999) {
      return null;
    }
    StringBuffer sb = new StringBuffer();
    int[] scale = {1000, 500, 100, 50, 10, 5, 1};
    for (int i = 0; i < scale.length; i = i+2) {
      int n = num/scale[i];
      if (n > 0) {
        if (n == 4) {
          sb.append(romanMap.get(scale[i]) + romanMap.get(scale[i-1]));
        } else if (n == 9) {
          sb.append(romanMap.get(scale[i]) + romanMap.get(scale[i-2]));
        } else if (n < 5) {
          for (int k = 0; k < n; k++) {
            sb.append(romanMap.get(scale[i]));
          }
        } else {
          sb.append(romanMap.get(scale[i-1]));
          n = n -5;
          for (int k = 0; k < n; k++) {
            sb.append(romanMap.get(scale[i]));
          }
        }
      }
      num = num % scale[i];
    }
    return sb.toString();
  }

  public int integerBreak(int n) {
    if (n <= 1) return n;
    if (n <= 4) {
      if (n == 2) return 1;
      if (n == 3) return 2;
      if (n == 4) return 4;
    }
    int product = 1;
    while (n > 4) {
      product = product * 3;
      n = n - 3;
    }
    if (n > 0) {
      return product * n;
    }
    return product;
  }

  public static void main(String[] args) {

  }
}
