package midas.algorithm;

import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Created by xli1 on 11/9/15.
 */

class Interval {
  int start;
  int end;
  Interval() { start = 0; end = 0; }
  Interval(int s, int e) { start = s; end = e; }
}

class NumArray {
  int[] partSum = null;
  public NumArray(int[] nums) {
    if (nums != null) {
      partSum = new int[nums.length];
      int sum = 0;
      for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        partSum[i] = sum;
      }
    }
  }

  public int sumRange(int i, int j) {
    if (partSum == null || i > j || i < 0) throw new IllegalArgumentException();
    if (i == 0) return partSum[j];
    return partSum[j] - partSum[i-1];
  }
}

class NumMatrix {
  int[][] partialSum = null;
  public NumMatrix(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      partialSum = null;
    } else {
      partialSum = new int[matrix.length][matrix[0].length];
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
          if (j == 0) partialSum[i][j] = matrix[i][j];
          else {
            partialSum[i][j] = partialSum[i][j - 1] + matrix[i][j];
          }
        }
      }
    }
  }

  public int sumRegion(int row1, int col1, int row2, int col2) {
    if (partialSum == null) return 0;
    if (row1 > row2 || col1 > col2) {
      throw new IllegalArgumentException();
    }
    int sum = 0;
    for (int i = row1; i <= row2; i++) {
      if (col1 >= 1) {
        sum += partialSum[i][col2] - partialSum[i][col1 - 1];
      } else {
        sum += partialSum[i][col2];
      }
    }
    return sum;
  }
}

class MatrixOperation {
  public int[][] multiplySparseMatrix(int[][] A, int[][] B) {
    if (A == null || A.length == 0 || A[0].length == 0 || B == null || B.length == 0 || B[0].length == 0) {
      throw new IllegalArgumentException();
    }
    int rowA = A.length, colA = A[0].length, rowB = B.length, colB = B[0].length;
    int[][] res = new int[rowA][colB];
    for (int i = 0; i < rowA; i++) {
      for (int j = 0; j < colA; j++) {
        if (A[i][j] != 0) {
          for (int k = 0; k < colB; k++) {
            if (B[j][k] != 0) {
              res[i][k] += A[i][j] * B[j][k];
            }
          }
        }
      }
    }
    return res;
  }
}

public class ArrayOperation {
  public String convertZigZag(String s, int numRows) {
    if (s == null || s.length() <= 1 || numRows == 1) return s;
    int N = s.length();
    char[] arr = new char[N];
    int pos = 0;
    for (int i = 0; i < numRows; i++) {
      if (i == 0 || i == numRows -1) {
        int cur = i;
        while (cur < N) {
          arr[pos++] = s.charAt(cur);
          cur += 2 * (numRows - 1);
        }
      } else {
        int cur = i;
        boolean flip = true;
        while (cur < N) {
          arr[pos++] = s.charAt(cur);
          if (flip) {
            cur += 2 * (numRows - 1 - i);
          } else {
            cur += 2 * i;
          }
          flip = !flip;
        }
      }
    }
    return new String(arr);
  }

  public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    StringBuffer sb = new StringBuffer();
    Arrays.sort(strs, new Comparator<String>() {
      public int compare(String a, String b) {
        if (a.length() < b.length()) return -1;
        else if (a.length() == b.length()) return 0;
        else return 1;
      }
    });
    for (int i = 0; i < strs[0].length(); i++) {
      char c = strs[0].charAt(i);
      boolean isSame = true;
      for (int j = 0; j < strs.length; j++) {
        if (strs[j].charAt(i) != c) {
          isSame = false;
          break;
        }
      }
      if (!isSame) break;
      else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  public String longestCommonPrefix2(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    StringBuffer sb = new StringBuffer();
    int pos = 0;
    boolean exceed = false;
    while (!exceed) {
      char cur = 0;
      if (pos < strs[0].length()) {
        cur = strs[0].charAt(pos);
        boolean isSame = true;
        for (int i = 0; i < strs.length; i++) {
          if (pos >= strs[i].length() || cur != strs[i].charAt(pos)) {
            isSame = false;
            exceed = true;
            break;
          }
        }
        if (isSame) sb.append(cur);
      } else {
        exceed = true;
      }
      pos++;
    }
    return sb.toString();
  }

  public boolean canAttendMeetings(Interval[] intervals) {
    if (intervals == null || intervals.length <= 1) return true;
    Arrays.sort(intervals, (a, b) -> {
      return a.start - b.start;
    });
    for (int i = 1; i < intervals.length; i++) {
      if (intervals[i].start < intervals[i-1].end) {
        return false;
      }
    }
    return true;
  }

  public int minMeetingRooms(Interval[] intervals) {
    if (intervals == null || intervals.length == 0) return 0;
    Arrays.sort(intervals, (a, b)-> {
      return a.start - b.start;
    });
    int rooms = 0;
    PriorityQueue<Integer> endTime = new PriorityQueue<Integer>();
    for (int i = 0; i < intervals.length; i++) {
      if (endTime.isEmpty() || endTime.peek() > intervals[i].start) {
        rooms++;
        endTime.add(intervals[i].end);
      } else {
        endTime.remove();
        endTime.add(intervals[i].end);
      }
    }
    return rooms;
  }

  public static void main(String[] args) {
    ArrayOperation test = new ArrayOperation();
    System.out.println(test.convertZigZag("PAYPALISHIRING", 3));
    String[] arr = {"abc", "adji", "djie"};
    Arrays.sort(arr, new Comparator<String>() {
      public int compare(String a, String b) {
        if (a.length() < b.length()) return -1;
        else if (a.length() == b.length()) return 0;
        else return 1;
      }
    });
    for (String x : arr) System.out.println(x);
  }
}
