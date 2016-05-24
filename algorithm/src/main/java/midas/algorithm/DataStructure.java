package midas.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import midas.data.NestedInteger;

class NestedIterator implements Iterator<NestedInteger> {
  private Stack<NestedInteger> stack;
  public NestedIterator(List<NestedInteger> nestedList) {
    stack = new Stack<NestedInteger>();
    for (int i = nestedList.size()-1; i >= 0; i--) {
      stack.push(nestedList.get(i));
    }
  }

  public NestedInteger next() {
    if (!hasNext()) {
      return null;
    }
    return stack.pop();
  }

  public boolean hasNext() {
    if (stack.isEmpty()) {
      return false;
    }
    if (stack.peek().isInteger()) {
      return true;
    } else {
      while (!stack.isEmpty() && !stack.peek().isInteger()) {
        List<NestedInteger> top = stack.pop().getList();
        for (int i = top.size() - 1; i >= 0; i--) {
          stack.push(top.get(i));
        }
      }
      if (stack.isEmpty()) return false;
      return true;
    }
  }
}

class MyInteger implements NestedInteger {
  Integer value = null;
  List<NestedInteger> list = null;
  public MyInteger(Integer val) {
    value = val;
  }
  public MyInteger(List<NestedInteger> l) {
    list = l;
  }
  public boolean isInteger() {
    if (value != null) {
      return true;
    }
    return false;
  }
  public Integer getInteger() {
    return value;
  }
  public List<NestedInteger> getList() {
    return list;
  }
}

public class DataStructure {
  public static void main(String[] args) {
    List<NestedInteger> testlist = new ArrayList<NestedInteger>();
    NestedInteger first = new MyInteger(1);
    NestedInteger second = new MyInteger(1);
    List<NestedInteger> firstlist = new ArrayList<NestedInteger>();
    firstlist.add(first);
    firstlist.add(second);
    NestedInteger firstlistInteger = new MyInteger(firstlist);
    testlist.add(firstlistInteger);
    NestedInteger secondInteger = new MyInteger(2);
    testlist.add(secondInteger);

    NestedIterator iter = new NestedIterator(testlist);
    while(iter.hasNext()) {
      System.out.println(iter.next().getInteger());
    }
  }
}