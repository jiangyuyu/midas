package midas.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

  public List<String> findStrobogrammatic(int n) {
    if (n <= 0) {
      return null;
    }
    List<String> ret = new ArrayList<String>();
    char[] temp = new char[n];
    findStrobogrammatic(n, 0, temp, ret);
    return ret;
  }
  public void findStrobogrammatic(int n, int pos, char[] temp, List<String> ret) {
    if (pos == (n-1)/2) {
      if (n%2 == 0) {
        if (pos != 0) {
          ret.add(new String(fill(temp, pos, pos + 1, '0', '0')));
        }
        ret.add(new String(fill(temp, pos, pos + 1, '6', '9')));
        ret.add(new String(fill(temp, pos, pos + 1, '9', '6')));
        ret.add(new String(fill(temp, pos, pos + 1, '1', '1')));
        ret.add(new String(fill(temp, pos, pos + 1, '8', '8')));
      } else {
        ret.add(new String(fill(temp, pos, pos, '0', '0')));
        ret.add(new String(fill(temp, pos, pos, '1', '1')));
        ret.add(new String(fill(temp, pos, pos, '8', '8')));
      }
      return;
    }
    if (pos != 0) {
      findStrobogrammatic(n, pos+1, fill(temp, pos, n-pos-1, '0', '0'), ret);
    }
    findStrobogrammatic(n, pos+1, fill(temp, pos, n-pos-1, '6', '9'), ret);
    findStrobogrammatic(n, pos+1, fill(temp, pos, n-pos-1, '9', '6'), ret);
    findStrobogrammatic(n, pos+1, fill(temp, pos, n-pos-1, '1', '1'), ret);
    findStrobogrammatic(n, pos+1, fill(temp, pos, n-pos-1, '8', '8'), ret);
  }

  private char[] fill(char[] arr, int left, int right, char leftc, char rightc) {
    arr[left] = leftc;
    arr[right] = rightc;
    return arr;
  }

  public int strobogrammaticInRange(String low, String high) {
    if (low.length()>high.length() || (low.length() == high.length() && low.compareTo(high) > 0)) {
      return 0;
    }
    int lowlength = low.length(), highlength = high.length();
    int ret = 0;
    for (int l = lowlength; l <= highlength; l++) {
      char[] temp = new char[l];
      ret += findStrobogrammatic(l, temp, 0, low, high);
    }
    return ret;
  }

  public int findStrobogrammatic(int n, char[] temp, int pos, String lowbound, String highbound) {
    if (pos == (n-1)/2) {
      int total = 0;
      if (n%2 == 0) {
        if (pos > 0 && fillCheck(temp, pos, pos+1, '0', '0', lowbound, highbound)) {
          total++;
        }
        if (fillCheck(temp, pos, pos+1, '6', '9', lowbound, highbound)) {
          total++;
        }
        if (fillCheck(temp, pos, pos+1, '9', '6', lowbound, highbound)) {
          total++;
        }
        if (fillCheck(temp, pos, pos+1, '1', '1', lowbound, highbound)) {
          total++;
        }
        if (fillCheck(temp, pos, pos+1, '8', '8', lowbound, highbound)) {
          total++;
        }
      } else {
        if (fillCheck(temp, pos, pos, '0', '0', lowbound, highbound)) {
          total++;
        }
        if (fillCheck(temp, pos, pos, '1', '1', lowbound, highbound)) {
          total++;
        }
        if (fillCheck(temp, pos, pos, '8', '8', lowbound, highbound)) {
          total++;
        }
      }
      return total;
    }
    int total = 0;
    if (pos > 0) total += findStrobogrammatic(n, fill(temp, pos, n-pos-1, '0', '0'), pos+1, lowbound, highbound);
    total += findStrobogrammatic(n, fill(temp, pos, n-pos-1, '1', '1'), pos+1, lowbound, highbound);
    total += findStrobogrammatic(n, fill(temp, pos, n-pos-1, '8', '8'), pos+1, lowbound, highbound);
    total += findStrobogrammatic(n, fill(temp, pos, n-pos-1, '6', '9'), pos+1, lowbound, highbound);
    total += findStrobogrammatic(n, fill(temp, pos, n-pos-1, '9', '6'), pos+1, lowbound, highbound);
    return total;
  }

  private boolean fillCheck(char[] arr, int left, int right, char leftc, char rightc, String low, String high) {
    arr[left] = leftc;
    arr[right] = rightc;
    String str = new String(arr);
    return (str.length() > low.length() || (str.length() == low.length() && str.compareTo(low) >= 0)) &&
        (str.length() < high.length() || (str.length() == high.length() && str.compareTo(high) <= 0));
  }

  public List<List<Integer>> getFactors(int n) {
    Set<List<Integer>> result = new HashSet<>();

    int dist = (int) Math.sqrt(n);

    for (int i = 2; i <= dist; i++) {
      if (n % i == 0) {
        List<List<Integer>> tmp = helper(n / i);
        for (List<Integer> l : tmp) {
          l.add(i);
          Collections.sort(l);
          result.add(l);
        }
      }
    }
    return new ArrayList<>(result);
  }

  public List<List<Integer>> helper(int n) {
    List<List<Integer>> result = new ArrayList<>();

    List<Integer> t = new ArrayList<>();
    t.add(n);
    result.add(t);

    int dist = (int) Math.sqrt(n);

    for (int i = 2; i <= dist; i++) {
      if (n % i == 0) {
        List<List<Integer>> tmp = helper(n / i);
        for (List<Integer> l : tmp) {
          l.add(i);
          result.add(l);
        }
      }
    }
    return result;
  }

  public static void main(String[] args) {
    Backtracking test = new Backtracking();
//    System.out.println(test.letterCombinationsIter("23"));
    System.out.println(test.getFactors(16));
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
