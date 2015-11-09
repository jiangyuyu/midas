package com.midas.algorithm;

/**
 * Created by xli1 on 11/6/15.
 */
public class Sort {
  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if ((nums1 == null && nums2 == null) || (nums1.length == 0 && nums2.length == 0)) throw new IllegalArgumentException();
    int N1 = nums1.length, N2 = nums2.length;
//    int x = findKth(nums1, 0, nums2, 0, (N1 + N2 - 1)/2);
    int x = findKthLinear(nums1, nums2, (N1 + N2 -1)/2);
    System.out.println(x);
    if ((N1 + N2) %2 == 1) return x;
//    int y = findKth(nums1, 0, nums2, 0, (N1 + N2)/2);
    int y = findKthLinear(nums1, nums2, (N1 + N2)/2);
    System.out.println(y);
    return (x + y)/2.0;
  }

  public int findKth(int[] a, int i, int[] b, int j, int pos) {// pos is relative position
    if (a == null || a.length == i) return b[j+pos];
    if (b == null || b.length == j) return a[i+pos];
    if (pos == 0) return a[i] < b[j] ? a[i] : b[j];
    int k = (pos-1)/2;
    int x = Math.min(a.length-1, i + k);
    int y = Math.min(b.length-1, j + k);
    if (a[x] < b[y]) return findKth(a, x+1, b, j, (pos - (x-i+1)));
    return findKth(a, i, b, y+1, (pos - (y-j+1)));
  }

  public int findKthLinear(int[] a, int[] b, int pos) {// pos is absolute position
    if (a == null || a.length == 0) return b[pos];
    if (b == null || b.length == 0) return a[pos];
    int i = 0, j = 0;
    while (i < a.length && j < b.length && (i+j) < pos) {
      if (a[i] <= b[j]) i++;
      else j++;
    }
    if (i == a.length) return b[pos - i];
    if (j == b.length) return a[pos - j];
    return a[i] < b[j] ? a[i] : b[j];
  }

  public static void main(String[] args) {
    Sort test = new Sort();
    int[] nums1 = {2};
    int[] nums2 = {1, 3, 4};
    System.out.println(test.findMedianSortedArrays(nums1, nums2));
  }
}
