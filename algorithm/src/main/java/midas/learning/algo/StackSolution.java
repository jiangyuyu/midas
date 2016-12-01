package midas.learning.algo;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import midas.data.NestedInteger;


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

  public int largestRectangleArea(int[] heights) {
    if (heights == null || heights.length == 0) return 0;
    int n = heights.length;
    int max = 0;
    Stack<Integer> hstack = new Stack<Integer>();
    Stack<Integer> istack = new Stack<Integer>();
    for (int i = 0; i < n; i++) {
      int h = heights[i];
      if (hstack.isEmpty() || h > hstack.peek()) {
        hstack.push(h);
        istack.push(i);
      } else if (h < hstack.peek()){
        int index = 0;
        while (!hstack.isEmpty() && h <= hstack.peek()) {
          index = istack.pop();
          max = Math.max(max,  hstack.pop()* (i-index));
        }
        hstack.push(h);
        istack.push(index);
      }
    }
    while (!hstack.isEmpty()) {
      max = Math.max(max, hstack.pop() * (n-istack.pop()));
    }
    return max;
  }

  public String removeKdigits(String num, int k) {
    if (num == null) return num;
    int n = num.length();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < n; i++) {
      char c = num.charAt(i);
      while (k > 0 && sb.length() > 0 && c < sb.charAt(sb.length()-1)) {
        sb.deleteCharAt(sb.length()-1);
        k--;
      }
      sb.append(c);
    }
    while (sb.length() > n-k) sb.deleteCharAt(sb.length()-1);
    while (sb.length() > 0 && sb.charAt(0) == '0') sb.deleteCharAt(0);
    if (sb.length() == 0) return "0";
    return sb.toString();
  }

  public String removeKDigits(String num, int k) {
    if (num == null) return num;
    int n = num.length();
    Stack<Character> stack = new Stack<Character>();
    for (int i = 0; i < n; i++) {
      char c = num.charAt(i);
      while (k > 0 && !stack.isEmpty() && c < stack.peek()) {
        stack.pop();
        k--;
      }
      stack.push(c);
    }
    while (stack.size() > n-k) stack.pop();
    int i = 0;
    for (i = 0; i < stack.size() && stack.get(i) == '0'; i++);
    StringBuffer sb = new StringBuffer();
    for (; i < stack.size(); i++) sb.append(stack.get(i));
    if (sb.length() == 0) return "0";
    return sb.toString();
  }

  public int calculator(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    int ret = 0;
    Stack<Integer> ops = new Stack<Integer>();
    Stack<Character> operator = new Stack<Character>();
    operator.push('(');
    int i = 0;
    while (i < n) {
      char cur = s.charAt(i);
      if (cur == ' ') {
        i++;
      } else if (cur >= '0' && cur <= '9') {
        StringBuffer sb = new StringBuffer();
        while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
          sb.append(s.charAt(i));
          i++;
        }
        ops.push(Integer.parseInt(sb.toString()));
      } else if (cur == '+' || cur == '-') {
        if (operator.peek() == '(') {
          operator.push(cur);
        } else {
          eval(ops, operator);
          operator.push(cur);
        }
        i++;
      } else if (cur == '(') {
        operator.push(cur);
        i++;
      } else if (cur == ')') {
        while(operator.peek() != '(') {
          eval(ops, operator);
        }
        operator.pop();
        i++;
      }
    }
    while (operator.size() > 1) {
      eval(ops, operator);
    }
    return ops.peek();
  }

  private void eval(Stack<Integer> ops, Stack<Character> operator) {
    int a = ops.pop();
    int b = ops.pop();
    char op = operator.pop();
    if (op == '+') ops.push(a + b);
    else if (op == '-') ops.push(b-a);
  }

  public int maxRectangle(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
    int m = matrix.length, n = matrix[0].length;
    int[] h = new int[n];
    Stack<Integer> hstack = new Stack<Integer>();
    Stack<Integer> istack = new Stack<Integer>();
    int max = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] == '1') h[j]++;
        else h[j] = 0;
        if (hstack.isEmpty() || h[j] > hstack.peek()) {
          hstack.push(h[j]);
          istack.push(j);
        } else if (h[j] < hstack.peek()){
          int index = 0;
          while (!hstack.isEmpty() && h[j] <= hstack.peek()) {
            index = istack.pop();
            int height = hstack.pop();
            max = Math.max(max, height * (j-index));
          }
          hstack.push(h[j]);
          istack.push(index);
        }
      }
      while (!hstack.isEmpty()) {
        max = Math.max(max, hstack.pop() * (n - istack.pop()));
      }
    }
    return max;
  }

//  public NestedInteger miniParser(String s) {
//    if (s == null || s.isEmpty()) return null;
//    Stack<NestedInteger> stack = new Stack<NestedInteger>();
//    StringBuffer sb = new StringBuffer();
//    for (char c : s.toCharArray()) {
//      if (c == '[') {
//        stack.push(new NestedInteger());
//      } else if (c == ']') {
//        if (sb.length() != 0) {
//          stack.peek().add(new NestedInteger(Integer.parseInt(sb.toString())));
//          sb.setLength(0);
//        }
//        NestedInteger top = stack.pop();
//        if (!stack.isEmpty()) {
//          stack.peek().add(top);
//        } else {
//          return top;
//        }
//      } else if (c == ',') {
//        if (sb.length() != 0) {
//          stack.peek().add(new NestedInteger(Integer.parseInt(sb.toString())));
//          sb.setLength(0);
//        }
//      } else {
//        sb.append(c);
//      }
//    }
//    if (sb.length() > 0) return new NestedInteger(Integer.parseInt(sb.toString()));
//    return null;
//  }

  public String removeDuplicateLetters(String s) {
    if (s == null && s.length() <= 1) return s;
    int[] count = new int[256]; // if this character will appear later
    int[] visited = new int[256]; // if this char has been added before
    for (char c : s.toCharArray()) {
      count[c]++;
    }
    Stack<Character> stack = new Stack<Character>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      count[c]--;
      if (visited[c] == 0) {
        while (!stack.isEmpty() && c < stack.peek() && count[stack.peek()] > 0) {
          visited[stack.pop()] = 0;
        }
        stack.push(c);
        visited[c]++;
      }
    }
    StringBuffer sb = new StringBuffer();
    for (Character c : stack) sb.append(c);
    return sb.toString();
  }

  public String decodeString(String s) {
    if (s == null || s.isEmpty()) return s;
    Stack<Integer> cstack = new Stack<Integer>();
    Stack<String> sstack = new Stack<String>();
    String res = "";
    int i = 0;
    while (i < s.length()) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        int cnt = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
          cnt = cnt * 10 + s.charAt(i) - '0';
          i++;
        }
        cstack.push(cnt);
      } else if (c == '[') {
        sstack.push(res); // save current expanded results, will be prefix later.
        res = "";
        i++;
      } else if (c == ']') {
        StringBuffer sb = new StringBuffer(sstack.pop());
        int cnt = cstack.pop();
        for (int j = 0; j < cnt; j++) {
          sb.append(res);
        }
        res = sb.toString();
        i++;
      } else {
        res += c;
        i++;
      }
    }
    return res;
  }

  public String parseTernary(String expression) {
    if (expression == null || expression.length() == 0) return "";
    Stack<Character> stack = new Stack<>();
    int n = expression.length();
    for (int i = n-1; i >= 0; i--) {
      char c = expression.charAt(i);
      if (!stack.isEmpty() && stack.peek() == '?') {
        stack.pop();
        char first = stack.pop();
        stack.pop();
        char second = stack.pop();
        if (c == 'T') stack.push(first);
        else stack.push(second);
      } else {
        stack.push(c);
      }
    }
    return String.valueOf(stack.peek());
  }

  public static void main(String[] args) {
    String[] tokens = {"0", "3", "/"};
    StackSolution test = new StackSolution();
    System.out.println(test.parseTernary("T?F?2:3:4"));
  }
}

class SnakeGame {
  int width, height;
  private int[][] food;
  private int curfood;
  private LinkedList<int[]> snake;
  private HashSet<String> snakeOccupy;

  /** Initialize your data structure here.
   @param width - screen width
   @param height - screen height
   @param food - A list of food positions
   E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
  public SnakeGame(int width, int height, int[][] food) {
    this.width = width;
    this.height = height;
    this.food = food;
    curfood = 0;
    int[] init = new int[2];
    snake.add(init);
    snakeOccupy = new HashSet<String>();
    snakeOccupy.add(Arrays.toString(init));
  }

  /** Moves the snake.
   @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
   @return The game's score after the move. Return -1 if game over.
   Game over when snake crosses the screen boundary or bites its body. */
  public int move(String direction) {
    int[] head = snake.getFirst();
    int[] nexthead = new int[2];
    if (direction.equals("U")) nexthead[0] = head[0]+1;
    else if (direction.equals("D")) nexthead[0] = head[0]-1;
    else if (direction.equals("L")) nexthead[1] = head[1]-1;
    else if (direction.equals("R")) nexthead[1] = head[1]+1;
    else return -1;
    if (nexthead[0] < 0 || nexthead[0] >= height || nexthead[1] < 0 || nexthead[1] >= width) return -1;
    if (nexthead[0] == food[curfood][0] && nexthead[1] == food[curfood][1]) {
      snake.add(0, nexthead);
      snakeOccupy.add(Arrays.toString(nexthead));
      return snake.size();
    }
    int[] tail = snake.getLast();
    snakeOccupy.remove(Arrays.toString(tail));
    snake.removeLast();
    if (snakeOccupy.contains(Arrays.toString(nexthead))) return -1;
    snake.addFirst(nexthead);
    snakeOccupy.add(Arrays.toString(nexthead));
    return snake.size();
  }
}

class MovingAverage {
  int capacity;
  LinkedList<Integer> queue = new LinkedList<Integer>();
  int sum = 0;
  public MovingAverage(int initsize) {
    capacity = initsize;
  }
  public double next(int elem) {
    if (queue.size() < capacity) {
      sum += elem;
      queue.add(elem);
    } else {
      int front = queue.poll();
      queue.add(elem);
      sum = sum - front + elem;
    }
    return (double)sum/queue.size();
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

class MyQueue {
  Stack<Integer> s1;
  Stack<Integer> s2;
  public MyQueue() {
    s1 = new Stack<Integer>();
    s2 = new Stack<Integer>();
  }
  // Push element x to the back of queue.
  public void push(int x) {
    s1.push(x);
  }

  // Removes the element from in front of queue.
  public void pop() {
    if (s2.isEmpty()) {
      while (!s1.isEmpty()) {
        s2.push(s1.pop());
      }
    }
    if (!s2.isEmpty()) s2.pop();
  }

  // Get the front element.
  public int peek() {
    if (empty()) throw new EmptyStackException();
    if (s2.isEmpty()) {
      while (!s1.isEmpty()) s2.push(s1.pop());
    }
    return s2.peek();
  }

  // Return whether the queue is empty.
  public boolean empty() {
    return (s1.isEmpty() && s2.empty());
  }
}

class MyStack {
  Queue<Integer> q1 = new LinkedList<Integer>();
  Queue<Integer> q2 = new LinkedList<Integer>();
  // Push element x onto stack.
  public void push(int x) {
    if (q1.isEmpty()) {
      q1.add(x);
    } else {
      while (!q1.isEmpty()) {
        q2.add(q1.poll());
      }
      q1.add(x);
    }
  }

  // Removes the element on top of the stack.
  public void pop() {
    if (empty()) return;
    if (!q1.isEmpty()) {
      q1.poll();
    } else {
      while (q2.size() > 1) {
        q1.add(q2.poll());
      }
      Queue<Integer> temp = q2;
      q2 = q1;
      q1 = temp;
      q1.poll();
    }
  }

  // Get the top element.
  public int top() {
    if (empty()) return Integer.MIN_VALUE;
    if (!q1.isEmpty()) return q1.peek();
    else {
      while (q2.size() > 1) {
        q1.add(q2.poll());
      }
      Queue<Integer> temp = q2;
      q2 = q1;
      q1 = temp;
      return q1.peek();
    }
  }

  // Return whether the stack is empty.
  public boolean empty() {
    return q1.isEmpty() && q2.isEmpty();
  }
}

class NestedIteratorRecursion implements Iterator<Integer> {
  LinkedList<Integer> flatlist;
  public NestedIteratorRecursion(List<NestedInteger> list) {
    flatlist = new LinkedList<Integer>();
    for (int i = 0; i < list.size(); i++) {
      NestedInteger n = list.get(i);
      flatlist.addAll(flatten(n));
    }
  }
  private List<Integer> flatten(NestedInteger n) {
    List<Integer> ret = new LinkedList<Integer>();
    if (n.isInteger()) ret.add(n.getInteger());
    else {
      List<NestedInteger> list = n.getList();
      for (NestedInteger x : list) {
        List<Integer> l = flatten(x);
        ret.addAll(l);
      }
    }
    return ret;
  }

  public Integer next() {
    return flatlist.poll();
  }

  public boolean hasNext() {
    return !flatlist.isEmpty();
  }
}

class NestedIteratorStack implements Iterator<Integer> {
  private Stack<NestedInteger> stack;

  public NestedIteratorStack(List<NestedInteger> list) {
    stack = new Stack<NestedInteger>();
    if (list != null && !list.isEmpty()) {
      for (int i = list.size()-1; i >= 0; i--) {
        stack.push(list.get(i));
      }
    }
  }

  public Integer next() {
    if (!hasNext()) return null;
    return stack.pop().getInteger();
  }

  public boolean hasNext() {
    while(!stack.isEmpty() && !stack.peek().isInteger()) {
      NestedInteger node = stack.pop();
      List<NestedInteger> l = node.getList();
      for (int i = l.size()-1; i >= 0; i--) {
        stack.push(l.get(i));
      }
    }
    return !stack.isEmpty();
  }
}

class NestedIteratorQueue implements Iterator<Integer> {
  private LinkedList<NestedInteger> queue;

  public NestedIteratorQueue(List<NestedInteger> list) {
    queue = new LinkedList<NestedInteger>();
    for (NestedInteger i : list) queue.add(i);
  }

  public Integer next() {
    if (!hasNext()) return null;
    return queue.poll().getInteger();
  }

  public boolean hasNext() {
    while(!queue.isEmpty() && !queue.peek().isInteger()) {
      List<NestedInteger> l = queue.poll().getList();
      for (int i = l.size()-1; i >= 0; i--) {
        queue.add(0, l.get(i));
      }
    }
    return !queue.isEmpty();
  }
}