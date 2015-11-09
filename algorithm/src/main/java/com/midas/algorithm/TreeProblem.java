package com.midas.algorithm;

import java.util.Arrays;
import java.util.HashSet;

public class TreeProblem {
  public boolean containDuplicate(int[] array) {
    if (array == null || array.length == 1) return false;
    HashSet<Integer> elems = new HashSet<>();
    for (int a : array) {
      if (elems.contains(a)) return true;
      elems.add(a);
    }
    return false;
  }
  public boolean containDuplicate2(int[] array) {
    if (array == null || array.length == 1) return false;
    Arrays.sort(array);
    for (int i = 1; i < array.length; i++) {
      if (array[i] == array[i-1]) return true;
    }
    return false;
  }


}
