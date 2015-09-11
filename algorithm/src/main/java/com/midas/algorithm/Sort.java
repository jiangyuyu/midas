package com.midas.algorithm;

/**
 * Created by xli1 on 9/8/15.
 */
public class Sort {
  public int firstBadVersion(int n) {
    if (n == 0) {
      throw new IllegalArgumentException();
    }
    int low = 1, high = n, mid = 0;
    while (low < high) {
      mid = (low + (high-low)/2);
      if (isBadVersion(mid)) {
        high = mid-1;
      } else {
        low = mid + 1;
      }
    }
    return isBadVersion(low)? low : low+1;
  }

  public int findCelebrity(int n) {
    if (n <= 1) return -1;
    int candidate = 0;
    for (int i = 1; i < n; i++) {
      if (!knows(i, candidate)) {
        candidate = i;
      }
    }
    for (int i = 0; i < n; i++) {
      if (i == candidate) continue;
      if (!knows(i, candidate) || knows(candidate, i)) return -1;
    }
    return candidate;
  }

  private boolean knows(int a, int b) {
    return false;
  }

  private boolean isBadVersion(int x) {
    if (x < 1702766719) return false;
    else return true;
  }


  public int hIndex(int[] citations) {
    if (citations.length == 0) return 0;
    int N = citations.length;
    int[] cnt = new int[N + 1];
    for (int i = 0; i < N; i++) {
      if (citations[i] >= N) {
        cnt[N]++;
      } else {
        cnt[citations[i]]++;
      }
    }
    int total = 0, hindex = 0;
    for (int i = N; i >= 0; i--) {
      total += cnt[i];
      if (total >= i) {
        hindex = i;
        break;
      }
    }
    return hindex;
  }

  public int hIndexSorted(int[] citations) {
    if (citations.length == 0) return 0;
    int N = citations.length;
    int low = 0, high = N-1;
    while (low <= high) {
      int mid = low + (high - low)/2;
      if (citations[mid] == (N-mid))
        return citations[mid];
      else if (citations[mid] < (N-mid))
        low = mid+1;
      else
        high = mid-1;
    }
    return N-low;
    // low always points to a larger value
    // if citations[mid] > N-mid, high moves backward. citation[low] = citations[mid] > N-low, so return N-low
    // if citations[mid] < N-mid, low moves forward. citations[low] > N-mid.
    // because the final mid would be on the turning point of  < target and > target.
  }

//  public void wiggleSort(int[] s){
//    if(s == null || s.length == 0) return;
//    int n = s.length;
//    boolean flag = true;
//    int current = s[0];
//    for (int i = 0; i < n-1; i++) {
//      if ((flag && current > s[i+1]) || (!flag && current < s[i+1])) {
//        s[i] = s[i+1];  // not the right position for cur so shift left and cur value preserved
//      } else {
//        s[i] = current; // insert the preserved current value to appropriate position
//        current = s[i+1];
//      }
//      flag = !flag;
//    }
//    s[n-1] = current;
//  }

  public void wiggleSort(int[] s) {
    if (s == null || s.length <= 1) return;
    int temp = s[0];
    boolean flag = true;
    for (int i = 0; i < s.length-1; i++) {
      if ((flag && temp > s[i+1]) || (!flag && temp < s[i+1])) {
        s[i] = s[i+1];  // wrong order, swap.
      } else {
        s[i] = temp;
        temp = s[i+1];
      }
      flag = !flag;
    }
    s[s.length-1] = temp;
  }

  public static void main(String[] args) throws Exception{
    Sort s = new Sort();
    System.out.println(s.firstBadVersion(2126753390));
  }
}
