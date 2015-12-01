package midas.algorithm;

import java.util.LinkedList;


/**
 * Created by xli1 on 11/17/15.
 */
public class StackQueue {
  public boolean isValidParenthesis(String s) {
    if (s == null || s.length() == 0) return true;
    LinkedList<Character> stack = new LinkedList<Character>();
    char[] arr = s.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      char c = arr[i];
      if (c == '(' || c == '{' || c == '[') stack.push(c);
      else {
        if (stack.isEmpty() || !match(c, stack.pop())) return false;
      }
    }
    if (!stack.isEmpty()) return false;
    return true;
  }
  private boolean match(char c1, char c2) {
    return ((c1 == ')' && c2 == '(') || (c1 == ']' && c2 == '[') || (c1 == '}' && c2 == '{'));
  }
}


