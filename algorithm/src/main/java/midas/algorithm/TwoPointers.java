package midas.algorithm;

import java.util.Arrays;

public class TwoPointers {
  public int lengthOfLongestSubstringWORepeatChar(String s) {
    if (s == null || s.length() == 0) return 0;
    int[] counter = new int[256];
    Arrays.fill(counter, -1);
    int maxlen = 0, left = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (counter[c] == -1) {
        counter[c] = i;
      } else {
        maxlen = Math.max(maxlen, i - left);
        while (left <= counter[c]) {
          counter[s.charAt(left)] = -1;
          left++;
        }
        counter[c] = i;
      }
    }
    maxlen = Math.max(maxlen,s.length() - left);
    return maxlen;
  }

  public static void main(String[] args) {
    TwoPointers test = new TwoPointers();
    test.lengthOfLongestSubstringWORepeatChar("aa");
  }
}
