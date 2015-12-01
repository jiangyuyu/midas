package midas.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by xli1 on 11/17/15.
 */
public class Backtracking {
  HashMap<Character, String> digitToCharMap = new HashMap<>();
  private void fillDigitToCharMap() {
    digitToCharMap.put('2', "a b c");
    digitToCharMap.put('3', "d e f");
    digitToCharMap.put('4', "g h i");
    digitToCharMap.put('5', "j k l");
    digitToCharMap.put('6', "m n o");
    digitToCharMap.put('7', "p q r s");
    digitToCharMap.put('8', "t u v");
    digitToCharMap.put('9', "w x y z");
  }

  public List<String> letterCombinationsIter(String digits) {
    List<String> ret = new ArrayList<>();
    if (digits == null || digits.length() == 0) return ret;
    fillDigitToCharMap();
    for (int i = 0; i < digits.length(); i++) {
      char cur = digits.charAt(i);
      if (digitToCharMap.containsKey(cur)) {
        String[] chars = digitToCharMap.get(cur).split(" ");
        if (ret.isEmpty()) {
          ret.addAll(Arrays.asList(chars));
        } else {
          List<String> clone = new ArrayList<>();
          clone.addAll(ret);
          ret.clear();
          for (String s : clone) {
            for (String ch : chars) {
              ret.add(s + ch);
            }
          }
        }
      }
    }
    return ret;
  }

  public List<String> letterCombinations(String digits) {
    fillDigitToCharMap();
    List<String> ret = new ArrayList<String>();
    if (digits == null || digits.length() == 0) return ret;
    ret = letterCombinations(digits, 0);
    return ret;
  }
  public List<String> letterCombinations(String digits, int pos) {
    List<String> ret = new ArrayList<String>();
    if (pos == digits.length()) {
      ret.add("");
      return ret;
    }
    List<String> rec = letterCombinations(digits, pos+1);
    char cur = digits.charAt(pos);
    String[] chars = digitToCharMap.get(cur).split(" ");
    for (String s : rec) {
      for (String prefix : chars) {
        ret.add(prefix + s);
      }
    }
    return ret;
  }

  public List<String> generateParenthesis(int n) {
    return generateParenthesis(n, n, 0);
  }
  private List<String> generateParenthesis(int left, int right, int pos) {
    if (left == 0 && right == 0) {
      List<String> ret = new ArrayList<String>();
      ret.add("");
      return ret;
    }
    if (left < 0 || left > right) {
      return new ArrayList<String>();
    }
    List<String> ret = new ArrayList<String>();
    List<String> sol1 = generateParenthesis(left-1, right, pos+1);
    for (String s : sol1) {
      ret.add("(" + s);
    }
    List<String> sol2 = generateParenthesis(left, right-1, pos+1);
    for (String s: sol2) {
      ret.add(")" + s);
    }
    return ret;
  }

  public int depthSum (List<NestedInteger> input) {
    if (input == null || input.size() == 0) {
      return 0;
    }
    return depthSum(input, 1);
  }

  public int depthSum(List<NestedInteger> input, int depth) {
    if (input == null || input.size() == 0) {
      return 0;
    }
    int sum = 0;
    for (NestedInteger n : input) {
      if (n.isInteger()) {
        sum += n.getInteger() * depth;
      } else {
        sum += depthSum(n.getList(), depth+1);
      }
    }
    return sum;
  }

  /**
   * This is the interface that represents nested lists.
   * You should not implement it, or speculate about its implementation.
   */


  public static void main(String[] args) {
    Backtracking test = new Backtracking();
//    System.out.println(test.letterCombinationsIter("23"));
    System.out.println(test.generateParenthesis(3));
  }
}

interface NestedInteger
{
  /** @return true if this NestedInteger holds a single integer, rather than a nested list */
  boolean isInteger();

  /** @return the single integer that this NestedInteger holds, if it holds a single integer
   * Return null if this NestedInteger holds a nested list */
  Integer getInteger();

  /** @return the nested list that this NestedInteger holds, if it holds a nested list
   * Return null if this NestedInteger holds a single integer */
  List<NestedInteger> getList();
}
