package midas.algorithm;

import java.util.Arrays;

public class Backtracking {
  public int numberOfPatterns(int m, int n) {
    int[][] jumps = new int[10][10];
    jumps[1][3] = jumps[3][1] = 2;
    jumps[4][6] = jumps[6][4] = 5;
    jumps[7][9] = jumps[9][7] = 8;
    jumps[1][7] = jumps[7][1] = 4;
    jumps[2][8] = jumps[8][2] = 5;
    jumps[3][9] = jumps[9][3] = 6;
    jumps[1][9] = jumps[9][1] = jumps[3][7] = jumps[7][3] = 5;

    boolean[] visited = new boolean[10];
    Arrays.fill(visited, false);
    int res = 0;
    res += f(1, 0, 0, visited, jumps, m, n) * 4;
    res += f(2, 0, 0, visited, jumps, m, n) * 4;
    res += f(5, 0, 0, visited, jumps, m, n);
    return res;
  }

  public int f(int start, int len, int total, boolean[] visited, int[][] jumps, int m, int n) {
    len++;
    if (len >= m && len <= n) total++;
    if (len > n) return total;
    visited[start] = true;
    for (int next = 1; next <= 9; next++) {
      if (!visited[next] && (jumps[start][next] == 0 || visited[jumps[start][next]])) {
        total = f(next, len, total, visited, jumps, m, n);
      }
    }
    visited[start] = false;
    return total;
  }

  public static void main(String[] args) {
    Backtracking test = new Backtracking();
    System.out.println(test.numberOfPatterns(1, 2));
  }
}
