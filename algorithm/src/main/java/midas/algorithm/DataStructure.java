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

class NestedIteratorRecusion implements Iterator<NestedInteger> {
  List<NestedInteger> flattenList;
  Iterator<NestedInteger> iter;
  public NestedIteratorRecusion(List<NestedInteger> list) {
    flattenList = new ArrayList<NestedInteger>();
    initFlattenList(list);
    iter = flattenList.iterator();
  }

  public void initFlattenList(List<NestedInteger> list) {
    for (NestedInteger i : list) {
      if (i.isInteger()) {
        flattenList.add(i);
      } else {
        initFlattenList(i.getList());
      }
    }
  }
  public NestedInteger next() {
    return iter.next();
  }
  public boolean hasNext() {
    return iter.hasNext();
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

class MovingAverage {
  int[] buffer = null;
  int pnt = 0;
  int capacity = 0;
  int size = 0;
  /** Initialize your data structure here. */
  public MovingAverage(int size) {
    buffer = new int[size];
    capacity = size;
  }

  public double next(int val) {
    size = size == capacity ? capacity : size + 1;
    buffer[pnt%capacity] = val;
    pnt++;
    int sum = 0;
    for (int i : buffer) sum += i;
    return sum/(double)(size);
  }
}

public class DataStructure {
  public int depthSum(List<NestedInteger> nestedList) {
    if (nestedList == null || nestedList.size() == 0) return 0;
    return depthSumHelper(nestedList, 1);
  }
  public int depthSumHelper(List<NestedInteger> nestedList, int level) {
    int sum = 0;
    for (int i = 0; i < nestedList.size(); i++) {
      NestedInteger cur = nestedList.get(i);
      if (cur.isInteger()) {
        sum += level * cur.getInteger();
      } else {
        sum += depthSumHelper(cur.getList(), level + 1);
      }
    }
    return sum;
  }
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

    NestedIteratorRecusion iter = new NestedIteratorRecusion(testlist);
    while(iter.hasNext()) {
      System.out.println(iter.next().getInteger());
    }
  }
}