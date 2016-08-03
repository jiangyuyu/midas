package midas.learning.algo;

import java.util.Arrays;
import java.util.LinkedList;


public class StringHandler {
  public String removeDuplicateLetters(String s) {
    if (s == null || s.length() <= 1) return s;
    int[] count = new int[256];
    int[] visited = new int[256];
    Arrays.fill(visited, 0);
    Arrays.fill(count, 0);
    char[] arr = s.toCharArray();
    for (char c : arr) {
      count[c]++;
    }
    LinkedList<Character> stack = new LinkedList<Character>();
    for (char c : arr) {
      count[c]--;
      if (visited[c] == 1) continue;
      while (!stack.isEmpty() && c < stack.peekLast() && count[stack.peekLast()] > 0) {
        Character temp = stack.removeLast();
        visited[temp] = 0;
      }
      stack.add(c);
      visited[c] = 1;
    }
    StringBuilder res = new StringBuilder();
    for (Character c : stack) {
      res.append(c);
    }
    return res.toString();
  }
}
