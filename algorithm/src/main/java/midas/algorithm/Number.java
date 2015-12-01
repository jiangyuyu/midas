package midas.algorithm;

import java.util.HashMap;


/**
 * Created by xli1 on 11/9/15.
 */
public class Number {
  public int reverse(int x) {
    int sign = 1, ret = 0;
    if (x < 0) sign = -1;
    if (x == Integer.MIN_VALUE) return 0;
    int val = x;
    while (sign * val > 0) {
      if (sign > 0 && ret > Integer.MAX_VALUE/10) return 0;
      if (sign < 0 && ret < Integer.MIN_VALUE/10) return 0;
      ret = ret * 10 + val%10;
      val = val/10;
    }
    return ret;
  }

  public int myAtoi(String str) {
    if (str == null || str.length() == 0) return 0;
    if (str.equals(Integer.toString(Integer.MIN_VALUE))) return Integer.MIN_VALUE;

    char[] arr = str.trim().toCharArray();
    int i = 0, sign = 1, ret = 0;
    boolean foundSign = false;
    while (i < arr.length && (arr[i] == '-' || arr[i] == '+' || arr[i] == ' ')) {
      if (foundSign) return 0;
      if (arr[i] == '-') sign = -sign;
      if (arr[i] == '-' || arr[i] == '+') foundSign = true;
      i++;
    }
    while (i < arr.length && arr[i] == ' ') i++;
    if (i == arr.length) return 0;
    while (i < arr.length && ((arr[i] >= '0' && arr[i] <= '9') || arr[i] == ',')) {
      if (arr[i] == ',') continue;
      if (ret <= (Integer.MAX_VALUE - (arr[i] - '0'))/10)
        ret = ret * 10 + arr[i] - '0';
      else {
        if (sign > 0) return Integer.MAX_VALUE;
        else return Integer.MIN_VALUE;
      }
      i++;
    }
    return sign * ret;
  }

  public boolean isPalindrome(int val) {
    if (val < 0) return false;
    int orders = 0, x = val;
    while (x >= 10) {
      orders++;
      x = x/10;
    }
    x = val;
    while (orders > 0) {
      int left = x;
      for (int k = 0; k < orders; k++) {
        left = left/10;
      }
      if (left != x%10) return false;
      x = (x - (left * (int) Math.pow(10, orders)))/10;
      orders -= 2;
    }
    return true;
  }

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

  public String intToRoman(int num) {
    if (num <= 0 || num > 3999) return null;
    StringBuffer sb = new StringBuffer();
    int x = num;
    while (x > 0) {
      if (x/1000 > 0) {
        int digit = x/1000;
        for (int k = 0; k < digit; k++) sb.append('M');
        x = x%1000;
      } else if (x/100 > 0) {
        int digit = x/100;
        if (digit == 9) sb.append("CM");
        else if (digit == 4) sb.append("CD");
        else {
          if (digit >= 5) {
            sb.append("D");
            digit = digit - 5;
          }
          for (int k= 0; k < digit; k++) {
            sb.append("C");
          }
        }
        x = x%100;
      } else if (x/10 > 0) {
        int digit = x/10;
        if (digit == 9) sb.append("XC");
        else if (digit == 4) sb.append("XL");
        else {
          if (digit >=5 ) {
            sb.append("L");
            digit = digit - 5;
          }
          for (int k = 0; k < digit; k++) {
            sb.append("X");
          }
        }
        x = x % 10;
      } else if (x > 0) {
        int digit = x;
        if (digit == 9) sb.append("IX");
        else if (digit == 4) sb.append("IV");
        else {
          if (digit >= 5) {
            sb.append("V");
            digit = digit - 5;
          }
          for (int k = 0; k < digit; k++) sb.append("I");
        }
        x = 0;
      }
    }
    return sb.toString();
  }

  public int romanToInteger(String s) {
    if (s == null || s.trim().length() == 0) return 0;
    int ret = 0;
    fillIntMap();
    for (int i = 0; i < s.length(); i++) {
      int curOrder = intMap.get(s.charAt(i));
      int nextOrder = (i == s.length()-1)? -1 : intMap.get(s.charAt(i+1));
      if (curOrder < nextOrder) {
        ret += nextOrder - curOrder;
        i++;
      } else {
        ret += curOrder;
      }
    }
    return ret;
  }

  public String dec2bin (int i) {
    StringBuilder sb = new StringBuilder();
    while (i != 0) {
      if ((i & 1) == 1) {
        sb.insert(0, "1");
      } else {
        sb.insert(0, "0");
      }
      i = i >> 1;
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    Number test = new Number();
    System.out.println(test.isPalindrome(-2147447412));
    System.out.println(test.romanToInteger(test.intToRoman(2903)));
  }
}
