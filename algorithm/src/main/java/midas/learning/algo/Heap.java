package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class Heap {
  public int kthLargest(int[] array, int k) {
    if (array == null || array.length < k) throw new IllegalArgumentException();
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int i = 0; i < array.length; i++) {
      if (pq.size() < k) pq.add(array[i]);
      else {
        if (array[i] > pq.peek()) {
          pq.poll();
          pq.add(array[i]);
        }
      }
    }
    return pq.peek();
  }

  public boolean isUglyNumber(int n) {
    if (n <= 0) return false;
    if (n == 1) return true;
    while (n > 1 && n %2 == 0) n = n/2;
    while (n > 1 && n % 3 == 0) n = n/3;
    while (n > 1 && n % 5 == 0) n = n/5;
    return n == 1;
  }

  public int nthUglyNumber(int n) {
    if (n <= 0) return 0;
    if (n == 1) return 1;
    int idx2 = 0, idx3 = 0, idx5 = 0;
    ArrayList<Integer> list = new ArrayList<Integer>();
    list.add(1);
    for (int i = 1; i < n; i++) {
      int min2 = list.get(idx2) * 2;
      int min3 = list.get(idx3) * 3;
      int min5 = list.get(idx5) * 5;
      int min = Math.min(min2, Math.min(min3, min5));
      if (min == min2) idx2++;
      if (min == min3) idx3++;
      if (min == min5) idx5++;
      list.add(min);
    }
    return list.get(n-1);
  }

  // consider duplicate problem
  public int nthSuperUglyNumber(int n, int[] primes) {
    if (n <= 0 || primes == null || primes.length == 0) return 0;
    if (n == 1) return 1;
    int N = primes.length;
    int[] idx = new int[N];
    Arrays.fill(idx, 0);
    ArrayList<Integer> list = new ArrayList<Integer>();
    list.add(1);
    for (int i = 1; i < n; i++) {
      int[] temp = new int[N];
      int min = Integer.MAX_VALUE;
      for (int j = 0; j < N; j++) {
        temp[j] = list.get(idx[j]) * primes[j];
        if (min > temp[j]) min = temp[j];
      }
      for (int j = 0; j < N; j++) {
        if (min == temp[j]) idx[j]++;
      }
      list.add(min);
    }
    return list.get(n-1);
  }

  public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    List<int[]> ret = new ArrayList<int[]>();
    if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) return ret;
    PriorityQueue<int[]> minheap = new PriorityQueue<int[]>(new Comparator<int[]>() {
      public int compare(int[] a, int[] b) {
        return (nums1[a[0]] + nums2[a[1]]) - (nums1[b[0]] + nums2[b[1]]);
      }
    });
    minheap.add(new int[] {0, 0});
    while (!minheap.isEmpty() && ret.size() < k) {
      int[] pairIndex = minheap.poll();
      int i = pairIndex[0];
      int j = pairIndex[1];
      ret.add(new int[] {nums1[i], nums2[j]});
      if (j + 1 < nums2.length) minheap.add(new int[]{i, j+1});
      if (j == 0 && (i + 1) < nums1.length) minheap.add(new int[]{i+1, 0});
    }
    return ret;
  }

  public boolean IamStupid() {
    return false; // return false
    //comment 2
  }

  public boolean helloworld() {
    return false;
  }

  public static void main(String[] args) {
    int[] primes = {2, 7, 13, 19};
    Heap test = new Heap();
    int[] nums1 = {1, 7, 11};
    int[] nums2 = {2, 4, 6};
//    System.out.println(test.kSmallestPairs(nums1, nums2, 9));
    System.out.println(test.nthSuperUglyNumber(3, primes));
  }
}
