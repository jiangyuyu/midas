package midas.learning.algo;

import java.util.Stack;


public class StackSolution {
  public int longestValidParentheses(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(-1); // to identify the boundary of valid substring
    int lastInvalid = -1, maxLen = 0;
    for (int i = 0; i < n; i++) {
      char c = s.charAt(i);
      if (c == '(') {
        stack.push(i);
      } else {
        if (stack.size() == 1) {
          lastInvalid = i;
        } else {
          stack.pop();
          maxLen = Math.max(maxLen, i - Math.max(lastInvalid, stack.peek()));
        }
      }
    }
    return maxLen;
  }

  public static void main(String[] args) {
    StackSolution test = new StackSolution();
    System.out.println(test.longestValidParentheses("()()"));
  }
}
