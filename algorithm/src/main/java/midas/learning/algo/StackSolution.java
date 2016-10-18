package midas.learning.algo;

import java.util.Stack;


public class StackSolution {
  public boolean isValidParenthesis(String s) {
    if (s == null || s.length() <= 1) return false;
    int n = s.length();
    Stack<Character> stack = new Stack<Character>();
    for (int i = 0; i < n; i++) {
      char c = s.charAt(i);
      if (c == '(' || c == '[' || c == '{') stack.push(c);
      else if (c == ')' || c == ']' || c == '}'){
        if (stack.isEmpty()) return false;
        else if ((c == ')' && stack.peek() == '(') || (c == ']' && stack.peek() == '[') || (c == '}' && stack.peek() == '{')){
          stack.pop();
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    return stack.isEmpty();
  }

  public int evalRPN(String[] tokens) {
    Stack<Integer> operand = new Stack<Integer>();
    int ret = 0;
    for (int i = 0; i < tokens.length; i++) {
      if (tokens[i].length() == 1 && !Character.isDigit(tokens[i].charAt(0))) {
        if (operand.size() < 2) {
          throw new IllegalArgumentException();
        } else {
          int o1 = operand.pop();
          int o2 = operand.pop();
          switch(tokens[i]) {
            case "+": ret = o1+o2; break;
            case "-": ret = o2-o1; break;
            case "*": ret = o1 * o2; break;
            case "/": ret = o2/o1; break;
          }
          operand.push(ret);
        }
      } else {
        operand.push(Integer.parseInt(tokens[i]));
      }
    }
    return operand.peek();
  }

  public String simplifyPath(String path) {
    if (path == null || path.length() == 0) return path;
    String[] elem = path.split("/");
    Stack<String> stack = new Stack<String>();
    for (int i = 0; i < elem.length; i++) {
      if (elem[i].length() == 0 || elem[i].equals(".")) continue;
      else if (elem[i].equals("..")) {
        if (!stack.isEmpty()) {
          stack.pop();
        }
      } else {
        stack.push(elem[i]);
      }
    }
    StringBuilder sb = new StringBuilder();
    if (stack.isEmpty()) return "/";
    while (!stack.isEmpty()) {
      sb.insert(0, stack.pop());
      sb.insert(0, "/");
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    String[] tokens = {"0", "3", "/"};
    StackSolution test = new StackSolution();
    System.out.println(test.simplifyPath("/"));
  }
}

class MinStack {
  Stack<Integer> stack;
  Stack<Integer> minstack;
  /** initialize your data structure here. */
  public MinStack() {
    stack = new Stack<Integer>();
    minstack = new Stack<Integer>();
  }

  public void push(int x) {
    stack.push(x);
    if (minstack.isEmpty() || x <= minstack.peek()) minstack.push(x);
  }

  public void pop() {
    int val = stack.pop();
    if (val == minstack.peek()) {
      minstack.pop();
    }
  }

  public int top() {
    return stack.peek();
  }

  public int getMin() {
    return minstack.peek();
  }
}

