package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;


public class DP {
  public int climbStairs(int n) {
    if (n == 0 || n == 1) return 1;
    int prev1 = 1, prev2 = 1, total = 0;
    for (int i = 2; i <= n; i++) {
      total = prev1 + prev2;
      prev1 = prev2;
      prev2 = total;
    }
    return total;
  }

  public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int n = nums.length;
    int prev = nums[0], maxSum = nums[0];
    for (int i = 1; i < n; i++) {
      int cur = prev + nums[i] > nums[i] ? prev + nums[i] : nums[i];
      maxSum = cur > maxSum ? cur : maxSum;
      prev = cur;
    }
    return maxSum;
  }

  public int houseRobber(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int max = 0, n = nums.length;
    int[] f = new int[n];
    if (n > 0) {
      f[0] = nums[0];
      max = max > f[0] ? max : f[0];
    }
    if (n > 1) {
      f[1] = nums[1];
      max = max > f[1] ? max : f[1];
    }
    if (n > 2) {
      f[2] = nums[0] + nums[2];
      max = max > f[2]? max : f[2];
    }

    for (int i = 3; i < n; i++) {
      f[i] = Math.max(f[i-2], f[i-3]) + nums[i];
      max = max > f[i] ? max : f[i];
    }
    return max;
  }

  public int maxProductSubarray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    // f[i] = f[i-1] * a[i] || a[i] if a is always positive.
    int n = nums.length;
    int[] max = new int[n], min = new int[n];
    int maxProduct = nums[0];
    max[0] = nums[0];
    min[0] = nums[0];
    for (int i = 1; i < n; i++) {
      max[i] = Math.max(Math.max(max[i-1] * nums[i], nums[i]), min[i-1] * nums[i]);
      maxProduct = maxProduct > max[i] ? maxProduct: max[i];
      min[i] = Math.min(Math.min(min[i-1] * nums[i], nums[i]), max[i-1] * nums[i]);
    }
    return maxProduct;
  }

  public int uniqueBST(int n) {
    if (n <= 0) return 0;
    int[] f = new int[n+1];
    f[0] = 1;
    f[1] = 1;
    for (int i = 2; i <= n; i++) {
      for (int j = 1; j <= i; j++) {
        f[i] += f[j-1] * f[i-j]; // j as root!
      }
    }
    return f[n];
  }

  public int numOfDecode(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    int prev1 = 0, prev2 = 1;
    for (int i = 1; i <= n; i++) {
      int cur = 0;
      char c = s.charAt(i-1);
      if (c >= '1' && c <= '9') cur += prev2;
      if (i > 1) {
        int temp = Integer.parseInt(s.substring(i-2, i));
        if (temp >= 10 && temp <= 26) cur += prev1;
      }
      prev1 = prev2;
      prev2 = cur;
    }
    return prev2;
  }

  public int editDistance(String s, String t) {
    if (s == null && t == null) return 0;
    if (s == null || t == null) return s == null? t.length() : s.length();
    int m = s.length(), n = t.length();
    int[][] f = new int[m+1][n+1];
    f[0][0] = 0;
    for (int i = 1; i <= m; i++) f[i][0] = i;
    for (int j = 1; j <= n; j++) f[0][j] = j;
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (s.charAt(i-1) == t.charAt(j-1)) f[i][j] = f[i-1][j-1];
        else f[i][j] = 1 + Math.min(f[i-1][j-1], Math.min(f[i-1][j], f[i][j-1]));
      }
    }
    return f[m][n];
  }

  public int maxRectangular(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
    int r = matrix.length, c = matrix[0].length;
    int[] h = new int[c];
    int max = 0;
    Stack<Integer> hstack = new Stack<Integer>();
    Stack<Integer> istack = new Stack<Integer>();

    for (int i = 0; i < r; i++) {
      hstack.clear();
      istack.clear();
      for (int j = 0; j < c; j++) {
        if (matrix[i][j] == 1) h[j] = h[j]+1;
        else h[j] = 0;
        if (hstack.isEmpty() || h[j] > hstack.peek()) {
          hstack.push(h[j]);
          istack.push(j);
        } else if (h[j] < hstack.peek()){
          int lastIndex = -1;
          while (!hstack.isEmpty() && h[j] <= hstack.peek()) {
            int height = hstack.pop();
            int index = istack.pop();
            max = Math.max(max, height * (j - index));
            lastIndex = index;
          }
          hstack.push(h[j]);
          istack.push(lastIndex);
        }
      }
      while (!hstack.isEmpty()) {
        int height = hstack.pop();
        int index = istack.pop();
        max = Math.max(max, height * (c - index));
      }
    }
    return max;
  }

  public int minPathInTriangle(List<List<Integer>> triangle) {
    if (triangle == null || triangle.isEmpty()) return 0;
    int n = triangle.size();
    int[] f = new int[triangle.get(n-1).size()];
    int[] next = new int[triangle.get(n-1).size()];
    f[0] = triangle.get(0).get(0);

    for (int i = 1; i < n; i++) {
      List<Integer> curList = triangle.get(i);
      for (int j = 0; j < curList.size(); j++) {
        if (j == 0) next[j] = f[j] + curList.get(j);
        else if (j == curList.size()-1) {
          next[j] = f[j-1] + curList.get(j);
        } else {
          next[j] = Math.min(f[j-1], f[j]) + curList.get(j);
        }
      }
      for (int k = 0; k < f.length; k++) f[k] = next[k];
    }
    int min = Integer.MAX_VALUE;
    for (int val : f) min = min > val ? val : min;
    return min;
  }

  public boolean regexMatch(String s, String p) {
    if (s == null || p == null) return false;
    int m = s.length(), n = p.length();
    boolean[][] f = new boolean[m+1][n+1];
    f[0][0] = true;
    for (int i = 1; i <= m; i++) f[i][0] = false;
    for (int j = 1; j <= n; j++) {
      if (j == 1) f[0][j] = false;
      else f[0][j] = f[0][j-2] && p.charAt(j-1) == '*';
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        char curs = s.charAt(i-1);
        char curp = p.charAt(j-1);
        if (curp == '*') {
          if (j-2 < 0) return false;
          if (f[i][j-2]) f[i][j] = true;
          else {
            char prev = p.charAt(j-2);
            for (int k = i-1; k >=0 && (prev == '.' || s.charAt(k) == prev); k--) {
              if (f[k][j-2]) {
                f[i][j] = true;
                break;
              }
            }
          }
        } else {
          f[i][j] = f[i-1][j-1] && (curs == curp || curp == '.');
        }
      }
    }
    return f[m][n];
  }

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
        char curs = s.charAt(i-1);
        char curp = p.charAt(j-1);
        if (curp == '*') {
          for (int k = i; k >= 0; k--) {
            if (f[k][j-1]) f[i][j] = true;
          }
        } else {
          f[i][j] = f[i-1][j-1] && (curp == '?' || curp == curs);
        }
      }
    }
    return f[m][n];
  }

  public int uniquePath(int m, int n) {
    if (m == 0 || n == 0) return 0;
    int[][] f = new int[m][n];
    f[0][0] = 1;
    for (int i = 1; i < m; i++) f[i][0] = 1;
    for (int j = 1; j < n; j++) f[0][j] = 1;
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        f[i][j] = f[i-1][j] + f[i][j-1];
      }
    }
    return f[m-1][n-1];
  }

  public int uniquePathWithBlock(int[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
    int m = grid.length, n = grid[0].length;
    int[][] f = new int[m][n];
    for (int i = 0; i < m; i++) {
      f[i][0] = grid[i][0] == 0 && f[i-1][0] != 0 ? f[i-1][0]:0;
    }
    for (int j = 0; j < n; j++) {
      f[0][j] = grid[0][j] == 0 && f[0][j-1] != 0? f[0][j-1] : 0;
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (grid[i][j] == 1) f[i][j] = 0;
        else {
          f[i][j] = f[i-1][j] + f[i][j-1];
        }
      }
    }
    return f[m-1][n-1];
  }

  public int minPathSum(int[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
    int m = grid.length, n = grid[0].length;
    int[][] f = new int[m][n];
    f[0][0] = grid[0][0];
    for (int i = 1; i < m; i++) f[i][0] = f[i-1][0] + grid[i][0];
    for (int j = 1; j < n; j++) f[0][j] = f[0][j-1] + grid[0][j];
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        f[i][j] = Math.min(f[i-1][j], f[i][j-1]) + grid[i][j];
      }
    }
    return f[m-1][n-1];
  }
  public int maxProfitOneTransaction(int[] prices) {
    int maxProfit = 0;
    if (prices.length <= 1) return maxProfit;
    int lowest = prices[0];
    for (int i = 1; i < prices.length; i++) {
      if (prices[i] < lowest) {
        lowest = prices[i];
      } else {
        maxProfit = Math.max(maxProfit, prices[i] - lowest);
      }
    }
    return maxProfit;
  }
  public int maxStockProfitTwoTransactions(int[] prices) {
    if (prices == null || prices.length == 0) return 0;
    int n = prices.length;
    int[] left = new int[n], right = new int[n];
    int lowest = prices[0], curmax = 0;
    for (int i = 1; i < n; i++) {
      if (prices[i] < lowest) lowest = prices[i];
      else curmax = Math.max(curmax, prices[i] - lowest);
      left[i] = curmax;
    }
    int highest = prices[n-1];
    curmax = 0;
    for (int i = n-2; i >= 0; i--) {
      if (prices[i] > highest) highest = prices[i];
      else curmax = Math.max(curmax, highest - prices[i]);
      right[i] = curmax;
    }
    curmax = 0;
    for (int i = 0; i < n; i++) {
      curmax = Math.max(curmax, (left[i]+right[i]));
    }
    return curmax;
  }

  public int maxStockProfitKTransactions(int[] prices, int k) {
    if (prices == null || prices.length == 0 || k == 0) return 0;
    int n = prices.length;
    int[] global = new int[k+1];
    int[] local = new int[k+1];
    for (int i = 1; i < n; i++) { // why
      int diff = prices[i] - prices[i-1];
      for (int j = k; j > 0; j--) {
        local[j] = global[j-1] + Math.max(diff, 0) > local[j] + diff? global[j-1] + Math.max(diff, 0): local[j] + diff;
        global[j] = local[j] > global[j] ? local[j] : global[j];
      }
    }
    return global[k];
  }

  public int numDistinctSubsequences(String s, String t) {
    if (s == null || t == null) return 0;
    int m = s.length(), n = t.length();
    int[][] f = new int[m+1][n+1];
    f[0][0] = 1;
    for (int i = 1; i <= m; i++) f[i][0] = 1;
    for (int j = 1; j <= n; j++) f[0][j] = 0;
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        f[i][j] = f[i-1][j];
        if (s.charAt(i-1) == t.charAt(j-1)) f[i][j] += f[i-1][j-1];
      }
    }
    return f[m][n];
  }

  public int longestParenthesis(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    int[] f = new int[n+1];
    int maxlen = 0;
    for (int i = 1; i <= n; i++) {
      char c = s.charAt(i-1);
      int j = i-2-f[i-1];
      if (c == '(' || j < 0 || s.charAt(j) == ')') f[i] = 0;
      else {
        f[i] = f[i-1]+2+f[j];
        maxlen = Math.max(maxlen, f[i]);
      }
    }
    return maxlen;
  }

  public int minNumOfSquareSum(int n) {
    if (n == 0 || n == 1) return 1;
    int[] f = new int[n+1];
    Arrays.fill(f, Integer.MAX_VALUE);
    f[0] = 0;
    for (int i = 0; i <= n; i++) {
      for (int j = 1; i + j*j <= n; j++) {
        f[i + j*j] = Math.min(f[i+j*j], 1 + f[i]);
        System.out.println(Arrays.toString(f));
      }
    }
    return f[n];
  }

  public boolean isInterleave(String s1, String s2, String s) {
    if (s1 == null) return s == s2 || s.equals(s2);
    if (s2 == null) return s == s1 || s.equals(s1);
    int m = s1.length(), n = s2.length();
    if (s.length() != (m+n)) return false;
    boolean[][] f = new boolean[m+1][n+1];
    f[0][0] = true;
    for (int i = 1; i <= m; i++) f[i][0] = f[i-1][0] && s1.charAt(i-1) == s.charAt(i-1);
    for (int j = 1; j <= n; j++) f[0][j] = f[0][j-1] && s2.charAt(j-1) == s.charAt(j-1);
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        f[i][j] = (f[i-1][j] && s1.charAt(i-1) == s.charAt(i+j-1)) || (f[i][j-1] && s2.charAt(j-1) == s.charAt(i+j-1));
      }
    }
    return f[m][n];
  }

  public boolean isScramble(String s1, String s2) {
    if (s1 == null || s2 == null || s1.length() != s2.length()) return false;
    int n = s1.length();
    boolean[][][] f = new boolean[n][n][n+1];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        f[i][j][0] = true;
        f[i][j][1] = s1.charAt(i) == s2.charAt(j);
      }
    }
    for (int len = 2; len <= n; len++) {
      for (int i = 0; i <= n-len; i++) {
        for (int j = 0; j <= n-len; j++) {
          if (s1.substring(i, i+len).equals(s2.substring(j, j+len))) f[i][j][len] = true;
          else {
            for (int m = 1; m < len; m++) {
              if ((f[i][j][m] && f[i+m][j+m][len-m]) || (f[i][j+len-m][m] && f[i+m][j][len-m])) {
                f[i][j][len] = true; break;
              }
            }
          }
        }
      }
    }
    return f[0][0][n];
  }

  public int minCoinChange(int[] coins, int target) {
    if (coins == null || coins.length == 0 || target < 0) return -1;
    int[] f = new int[target+1];
    Arrays.fill(f, Integer.MAX_VALUE);
    f[0] = 0;
    for (int i = 1; i <= target; i++) {
      for (int j = 0; j < coins.length; j++) {
        if (i >= coins[j] && f[i-coins[j]] != -1) {
          f[i] = Math.min(f[i], f[i-coins[j]] + 1);
        }
      }
      if (f[i] == Integer.MAX_VALUE) f[i] = -1;
    }
    return f[target];
  }

  public int minCutPalindromeNaive(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    int[][] f = new int[n][n];
    for (int[] arr : f) Arrays.fill(arr, Integer.MAX_VALUE);
    for (int i = 0; i < n; i++) {
      f[i][i] = 0;
      if (i < n-1) {
        f[i][i+1] = s.charAt(i) == s.charAt(i+1) ? 0 : 1;
      }
    }
    for (int len = 3; len <= n; len++) {
      for (int i = 0; i <= n-len; i++) {
        if (isPalindrome(s.substring(i, i+len))) f[i][i+len-1] = 0;
        else {
          for (int m = 1; m < len; m++) {
            f[i][i+len-1] = Math.min(f[i][i+len-1], 1 + f[i][i+m-1] + f[i+m][i+len-1]);
          }
        }
      }
    }
    return f[0][n-1];
  }

  private boolean isPalindrome(String s) {
    if (s == null) return false;
    int i = 0, j = s.length()-1;
    while (i < j) {
      if (s.charAt(i++) != s.charAt(j--)) return false;
    }
    return true;
  }

  public int minCutPalindrome(String s) {
    if (s == null) return -1;
    int n = s.length();
    int[] f = new int[n];
    boolean[][] p = new boolean[n][n];
    for (int i = 0; i < n; i++) p[i][i] = true;
    for (int i = 0; i < n; i++) {
      if (s.charAt(i) == s.charAt(0) && (i <=2 || p[1][i-1])) {
        f[i] = 0;
        p[0][i] = true;
      } else {
        f[i] = Integer.MAX_VALUE;
        for (int j = i; j > 0; j--) {
          if (s.charAt(i) == s.charAt(j) && (i-j <= 2 || p[j+1][i-1])) {
            f[i] = Math.min(f[i], 1 + f[j-1]);
            p[j][i] = true;
          }
        }
      }
    }
    return f[n-1];
  }

  public int maxSquare(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
    int max = 0;
    int m = matrix.length, n = matrix[0].length;
    int[][] f = new int[m][n];
    for (int i = 0; i < m; i++) {
      f[i][0] = matrix[i][0] == '0' ? 0 : 1;
      max = Math.max(max, f[i][0]);
    }
    for (int j = 0; j < n; j++) {
      f[0][j] = matrix[0][j] == '0'? 0 : 1;
      max = Math.max(max, f[0][j]);
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (matrix[i][j] == '0') f[i][j] = 0;
        else {
          f[i][j] = Math.min(f[i-1][j], Math.min(f[i][j-1], f[i-1][j-1])) + 1;
          max = Math.max(max, f[i][j]);
        }
      }
    }
    return max*max;
  }

  public int nthUglyNumber(int n) {
    if (n <= 0) return 0;
    if (n == 1) return 1;
    List<Integer> ugly = new LinkedList<Integer>();
    ugly.add(1);
    int idx2 = 0, idx3 = 0, idx5 = 0;
    int cur = 0;
    for (int i = 2; i <= n; i++) {
      int m2 = ugly.get(idx2) * 2;
      int m3 = ugly.get(idx3) * 3;
      int m5 = ugly.get(idx5) * 5;
      cur = Math.min(m2, Math.min(m3, m5));
      if (cur == m2) idx2++;
      if (cur == m3) idx3++;
      if (cur == m5) idx5++;
      ugly.add(cur);
    }
    return cur;
  }

  public int paintFenceWays(int n, int k) {
    if (n == 0 || k == 0) return 0;
    if (n == 1) return k;
    int[] f = new int[n];
    f[0] = k;
    for (int i = 1; i < n; i++) {
      if (i < 2) f[i] = f[i-1] * k;
      else {
        f[i] = f[i-1] * (k-1) + f[i-2] * (k-1);
      }
    }
    return f[n-1];
  }

  public int paintFenceWays2(int n, int k) {
    if (n == 0 || k == 0) return 0;
    if (n == 1) return k;
    int[] diff = new int[n];
    int[] same = new int[n];
    diff[0] = k;
    same[0] = 0;
    for (int i = 1; i < n; i++) {
      diff[i] = same[i-1] * (k-1) + diff[i-1] * (k-1);
      same[i] = diff[i-1];
    }
    return diff[n-1] + same[n-1];
  }

  public int longestIncreasingSequence(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int n = nums.length;
    int[] f = new int[n];
    f[0] = 1;
    int longest = f[0];
    for (int i = 1; i < n; i++) {
      f[i] = 1;
      for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j]) f[i] = Math.max(f[i], f[j]+1);
      }
      longest = Math.max(longest, f[i]);
    }
    return longest;
  }

  public int longestIncreasingSequenceLog(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    ArrayList<Integer> l = new ArrayList<Integer>();
    l.add(nums[0]);
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] < l.get(0)) l.set(0, nums[i]);
      else if (nums[i] > l.get(l.size()-1)) l.add(nums[i]);
      else {
        int left = 0, right = l.size()-1, mid = 0;
        while (left <= right) {
          mid = left + (right - left)/2;
          if (l.get(mid) < nums[i]) left = mid+1;
          else if (l.get(mid) == nums[i]) {
            left = mid;
            break;
          } else {
            right = mid-1;
          }
        }
        l.set(left, nums[i]);
      }
    }
    return l.size();
  }

  public int maxBombEnemies(char[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
    int m = grid.length, n = grid[0].length;
    int[][] rowCount = new int[m][n];
    int[][] colCount = new int[m][n];
    for (int i = 0; i < m; i++) {
      int start = 0;
      while (start < n) {
        int j = start;
        while(j < n && grid[i][j] == 'W') j++;
        start = j;
        int count = 0;
        while (j < n && grid[i][j] != 'W') {
          if (grid[i][j] == 'E') count++;
          j++;
        }
        for (int k = start; k < j; k++) {
          if (grid[i][k] == '0') rowCount[i][k] = count;
        }
        start = j+1;
      }
    }
    for (int j = 0; j < n; j++) {
      int start = 0;
      while (start < m) {
        int i = start;
        while(i < m && grid[i][j] == 'W') i++;
        start = i;
        int count = 0;
        while (i < m && grid[i][j] != 'W') {
          if (grid[i][j] == 'E') count++;
          i++;
        }
        for (int k = start; k < i; k++) {
          if (grid[k][j] == '0') colCount[k][j] = count;
        }
        start = i+1;
      }
    }
    int max = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == '0') max = Math.max(max, rowCount[i][j] + colCount[i][j]);
      }
    }
    return max;
  }

  public int paintHouse(int[][] cost) {
    if (cost == null || cost.length == 0 || cost[0].length == 0) return 0;
    int n = cost.length, color = cost[0].length;
    int[][] f = new int[n][color];
    for (int i = 0; i < color; i++) {
      f[0][i] = cost[0][i];
    }
    for (int i = 1; i < n; i++) {
      f[i][0] = Math.min(f[i-1][1], f[i-1][2]) + cost[i][0];
      f[i][1] = Math.min(f[i-1][0], f[i-1][2]) + cost[i][1];
      f[i][2] = Math.min(f[i-1][0], f[i-1][1]) + cost[i][2];
    }
    return Math.min(f[n-1][0], Math.min(f[n-1][1], f[n-1][2]));
  }

  public int paintHouseKColors(int[][] cost) {
    if (cost == null || cost.length == 0 || cost[0].length == 0) return 0;
    int n = cost.length, k = cost[0].length;
    int[][] f = new int[n][k];
    for (int i = 0; i < k; i++) {
      f[0][i] = cost[0][i];
    }
    for (int i = 1; i < n; i++) {
      for (int j = 0; j < k; j++) {
        int min = Integer.MAX_VALUE;
        for (int t = 0; t < k; t++) {
          if (t != j) {
            min = Math.min(min, f[i-1][t]+ cost[i][j]);
          }
        }
        f[i][j] = min;
      }
    }
    int minCost = Integer.MAX_VALUE;
    for (int i = 0; i < k; i++) {
      minCost = Math.min(minCost, f[n-1][i]);
    }
    return minCost;
  }
  public int paintHouseKColorsNK(int[][] cost) {
    if (cost == null || cost.length == 0 || cost[0].length == 0) return 0;
    int n = cost.length, col = cost[0].length;
    int[] f = new int[col];
    int min1 = 0, min2 = 0;
    for (int i = 0; i < n; i++) {
      int lastmin1 = min1, lastmin2 = min2;
      min1 = Integer.MAX_VALUE;
      min2 = Integer.MAX_VALUE;
      for (int j = 0; j < col; j++) {
        if (f[j] != lastmin1 || lastmin1 == lastmin2) f[j] = lastmin1 + cost[i][j];
        else f[j] = lastmin2 + cost[i][j];
        if (f[j] >= min1) {
          min2 = Math.min(min2, f[j]);
        } else {
          min2 = min1;
          min1 = f[j];
        }
      }
    }
    return min1;
  }

  public int numWithUniqueDigits(int n) {
    if (n == 0) return 1;
    int[] f = new int[n+1];
    f[0] = 1;
    int numDigits = 0;
    int total = 1;
    for (int i = 1; i <= n; i++) {
      if (i == 1) {
        f[i] = 9;
        total += f[i];
      }
      else {
        f[i] = f[i-1] * (9-numDigits);
        numDigits++;
        total += f[i];
      }
    }
    return total;
  }

  public List<Integer> largestDivisibleSubset(int[] nums) {
    List<Integer> ret = new LinkedList<Integer>();
    if (nums == null || nums.length == 0) return ret;
    int n = nums.length;
    Arrays.sort(nums);
    int[] f = new int[n];
    int[] index = new int[n];
    Arrays.fill(f, 1);
    Arrays.fill(index, -1); // -1 means a set contains itself.

    int largest = 1, largestEnding = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i-1; j >= 0; j--) {
        if (nums[i]%nums[j] == 0 && f[i] < f[j]+1) {
          f[i] = f[j]+1;
          index[i] = j;
        }
      }
      if (largest < f[i]) {
        largest = f[i];
        largestEnding = i;
      }
    }
    for (int i = largestEnding; i != -1; i = index[i]) {
      ret.add(nums[i]);
    }
    return ret;
  }

  public int maxSumRectangleBruteForce(int[][] grid, int k) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
    int m = grid.length, n = grid[0].length;
    int[][] f = new int[m+1][n+1];
    int maxsum = Integer.MIN_VALUE;
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (i == 0 && j == 0) f[i][j] = grid[i-1][j-1];
        else if (i == 0) f[i][j] = f[i][j-1]+ grid[i-1][j-1];
        else if (j == 0) f[i][j] = f[i-1][j] + grid[i-1][j-1];
        else {
          f[i][j] = f[i-1][j] + f[i][j-1] - f[i-1][j-1] + grid[i-1][j-1];
        }
        for (int p = i-1; p >= 0; p--) {
          for (int q = j-1; q >= 0; q--) {
            int sum = f[i][j] - f[p][j] - f[i][q] + f[p][q];
            if (sum <= k && maxsum < sum) maxsum = sum;
          }
        }
      }
    }
    return maxsum;
  }

  public int maxSumRectangle(int[][] matrix, int k) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
    int m = matrix.length, n = matrix[0].length;
    int ret = Integer.MIN_VALUE;
    for (int p = 0; p < n; p++) {
      int[] sum = new int[m];
      for (int q = p; q < n; q++) {
        TreeSet<Integer> set = new TreeSet<Integer>();
        int tempsum = 0;
        for (int r = 0; r < m; r++) {
          sum[r] += matrix[r][q];
          tempsum += sum[r];
          if (tempsum < k) ret = Math.max(ret, tempsum);
          Integer x = set.ceiling(tempsum - k);
          if (x != null) ret = Math.max(ret, tempsum - x);
          set.add(tempsum);
        }
      }
    }
    return ret;
  }

  public int wiggleMaxLength(int[] nums) {
    if (nums == null) throw new IllegalArgumentException();
    int n = nums.length;
    if (n <= 1) return n;
    int[] pos = new int[n];
    int[] neg = new int[n];
    Arrays.fill(pos, 1);
    Arrays.fill(neg, 1);
    int longest = 1;
    for (int i = 1; i < n; i++) {
      for (int j = i-1; j >= 0; j--) {
        if (nums[i] > nums[j]) pos[i] = Math.max(pos[i], neg[j]+1);
        else if (nums[i] < nums[j]) neg[i] = Math.max(neg[i], pos[j]+1);
      }
      System.out.println(Arrays.toString(pos));
      System.out.println(Arrays.toString(neg));
      longest = Math.max(longest, Math.max(pos[i], neg[i]));
    }
    return longest;
  }

  public int dungeonGame(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return -1;
    int m = matrix.length, n = matrix[0].length;
    int[][] f = new int[m][n];
    f[m-1][n-1] = Math.max(1, 1- matrix[m-1][n-1]);
    for (int j = n-2; j >= 0; j--) {
      f[m-1][j] = Math.max(1, f[m-1][j+1] - matrix[m-1][j]);
    }
    for (int i = m-2; i >= 0; i--) {
      f[i][n-1] = Math.max(1, f[i+1][n-1] - matrix[i][n-1]);
    }
    for (int i = m-2; i>= 0; i--) {
      for (int j = n-2; j>=0; j--) {
        f[i][j] = Math.max(1, Math.min(f[i+1][j], f[i][j+1]) - matrix[i][j]);
      }
    }
    return f[0][0];
  }

  public int maxEnvelops(int[][] envelops) {
    if (envelops == null || envelops.length == 0 || envelops[0].length == 0) return 0;
    int n = envelops.length;
    Arrays.sort(envelops, new Comparator<int[]>() {
      public int compare(int[] a, int[] b) {
        return b[0]-a[0];
      }
    });
    int[] f = new int[n]; int max = 1;
    for (int i = 0; i < n; i++) {
      if (i == 0) f[i] = 1;
      else {
        f[i] = 1;
        for (int j = i-1; j >= 0; j--) {
          if (envelops[j][0] > envelops[i][0] && envelops[j][1] > envelops[i][1]) {
            f[i] = Math.max(f[i], f[j] + 1);
          }
        }
      }
      max = Math.max(max, f[i]);
    }
    return max;
  }

  public static void main(String[] args) {
    DP test = new DP();
    int[] arr = {0, 0};
    NumArray array = new NumArray(arr);
    char[][] grid = {"0E00".toCharArray(), "E0WE".toCharArray(), "0E00".toCharArray()};
    int[][] costs = {{1,0,1},{0,-2,3}};
    System.out.println(test.wiggleMaxLength(arr));
  }

}
class NumArray {
  int[] partSum;
  public NumArray(int[] nums) {
    if (nums == null) throw new IllegalArgumentException();
    int n = nums.length;
    partSum = new int[n];
    if (n > 0) {
      partSum[0] = nums[0];
      for (int i = 1; i < n; i++) {
        partSum[i] = partSum[i-1] + nums[i];
      }
    }
  }
  public int sumRange(int i, int j) {
    if (i >= partSum.length || j >= partSum.length) throw new IllegalArgumentException();
    if (i > j) {
      int temp = i;
      i = j;
      j = temp;
    }
    if (i == 0) return partSum[j];
    else return partSum[j] - partSum[i-1];
  }
}

class NumMatrix {
  int[][] partialSum;
  int row = 0;
  int col = 0;

  public NumMatrix(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      partialSum = null;
      return;
    }
    row = matrix.length;
    col = matrix[0].length;
    int m = matrix.length, n = matrix[0].length;
    partialSum = new int[m+1][n+1];
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        partialSum[i][j] = partialSum[i-1][j] + partialSum[i][j-1] - partialSum[i-1][j-1] + matrix[i-1][j-1];
      }
    }
  }

  public int sumRegion(int row1, int col1, int row2, int col2) {
    if (partialSum == null) return 0;
    return partialSum[row2 + 1][col2 + 1] - partialSum[row1][col2+1] - partialSum[row2+1][col1] + partialSum[row1][col1];
  }
}
