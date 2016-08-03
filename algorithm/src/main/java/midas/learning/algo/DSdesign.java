package midas.learning.algo;

class MovingAverage {
  int[] array;
  int pnt = 0;
  int actualSize = 0;
  public MovingAverage(int size) {
    array = new int[size];
  }
  public double next(int x) {
    if (actualSize < array.length) {
      array[pnt++] = x;
      actualSize++;
    } else {
      pnt = pnt % actualSize;
      array[pnt++] = x;
    }
    int sum = 0;
    for (int temp : array) sum += temp;
    return (double)sum/actualSize;
  }
}

public class DSdesign {
}
