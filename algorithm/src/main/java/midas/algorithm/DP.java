package midas.algorithm;

/**
 * Created by naixlee on 11/6/15.
 */
public class DP {
  public String longestPalindromeSimple(String s) {
    if (s == null || s.length() <= 1) return s;
    int N = s.length();
    int maxlen = 0;
    String maxSubstr = null;
    for (int i = 0; i < N; i++) {
      String s1 = expand(s, i, i);
      if (s1.length() > maxlen) {
        maxlen = s1.length();
        maxSubstr = s1;
      }
      String s2 = expand(s, i, i+1);
      if (s2.length() > maxlen) {
        maxlen = s2.length();
        maxSubstr = s2;
      }
    }
    return maxSubstr;
  }

  public String expand(String s, int i, int j) {
    int l = i, r = j;
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
      l--; r++;
    }
    return s.substring(l+1, r);
  }

  public String longestPalindrome(String s) {
    if (s == null) return s;
    int N = s.length();
    if (N <= 1) return s;
    char[] arr = new char[2 * N + 1];
    for (int i = 0; i < N; i++) {
      arr[i * 2] = '#';
      arr[i * 2 + 1] = s.charAt(i);
    }
    arr[N * 2] = '#';  // mistake 1
    int[] p = new int[2 * N + 1];  //save the length of the palindrome centers at arr[i]
    int maxlen = 0, maxcenter = 0;
    int center = 0, right = 0;
    for (int i = 1; i <= 2 * N; i++) {
      int mirror = 2 * center - i;
      if (mirror >= 0 && i + p[mirror] < right) p[i] = p[mirror];
      else {
        p[i] = right > i ? right-i : 0; // mistake 2
        while (i-p[i]-1 >= 0 && i + p[i] + 1 <= 2 * N && arr[i-p[i]-1] == arr[i+p[i]+1]) p[i]++;
        if (i + p[i] > right) { // mistake 3
          center = i;
          right = i + p[i];
          if (p[i] > maxlen) {
            maxlen = p[i];
            maxcenter = center;
          }
        }
      }
    }
    return s.substring((maxcenter - maxlen)/2, (maxcenter+maxlen)/2);
  }

  public boolean regexMatch(String s, String p) {
    if (s == null || p == null) return false;
    int m = s.length(), n = p.length();

    boolean[][] f = new boolean[m+1][n+1];
    f[0][0] = true;
    for (int i = 1; i <= m; i++) {
      f[i][0] = false;
    }
    for (int j = 1; j <= n; j++) {
      if (j == 1) f[0][j] = false;
      else {
        f[0][j] = p.charAt(j-1) == '*' && f[0][j-2];
      }
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (p.charAt(j-1) == '*') {
          if (f[i][j-2]) f[i][j] = true;
          else {
            f[i][j] = false;
            if (j < 2) return false;
            char prev = p.charAt(j-2);
            for (int k = i-1; k >= 0 && (s.charAt(k) == prev || prev == '.'); k--) {
              if (f[k][j-2]) {
                f[i][j] = true;
                break;
              }
            }
          }

        } else {
          f[i][j] = (p.charAt(j-1) == '.' || p.charAt(j-1) == s.charAt(i-1)) && f[i-1][j-1];
        }
      }
    }
    return f[m][n];
  }

  // support . and *
  public boolean wildcardMatch(String s, String p) {
    if (s == null || p == null) return false;

    int m = s.length(), n = p.length();
    boolean[][] f = new boolean[m+1][n+1];
    f[0][0] = true;
    for (int i = 1; i <= m; i++) {
      f[i][0] = false;
    }
    for (int j = 1; j <= n; j++) {
      f[0][j] = f[0][j-1] && p.charAt(j-1) == '*';
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        char cur = p.charAt(j-1);
        if (cur != '*') {
          if (cur == '?') {
            f[i][j] = f[i-1][j-1];
          } else {
            f[i][j] = f[i-1][j-1] && (cur == s.charAt(i-1));
          }
        } else {
          f[i][j] = false;
          for (int k = 0; k <= i; k++) {
            if (f[k][j-1] == true) {
              f[i][j] = true;
              break;
            }
          }
        }
      }
    }
    return f[m][n];
  }



  public static void main(String[] args) {
    DP test = new DP();
    System.out.println(test.regexMatch("aaa", "ab*ac*a"));
  }
}
