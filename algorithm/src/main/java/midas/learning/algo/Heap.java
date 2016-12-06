package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import midas.data.Interval;
import midas.data.ListNode;


public class Heap {
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

  public List<int[]> getSkyline(int[][] buildings) {
    List<int[]> ret = new LinkedList<int[]>();
    if (buildings == null || buildings.length == 0) return ret;
    List<int[]> height = new LinkedList<int[]>();
    for (int[] bldg : buildings) {
      height.add(new int[] {bldg[0], -bldg[2]});
      height.add(new int[] {bldg[1], bldg[2]});
    }
    // sort the height, first consider x-axis, then y-axis
    Collections.sort(height, new Comparator<int[]>() {
      public int compare(int[] a, int[] b) {
        if (a[0] != b[0]) {
          return a[0] - b[0];
        } else {
          return a[1] - b[1];
        }
      }
    });

    PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
      public int compare(Integer a, Integer b) {
        return b-a;
      }
    });
    pq.add(0);
    int prev = 0;
    for (int[] h : height) {
      if (h[1] < 0) {
        pq.add(-h[1]);
      } else {
        pq.remove(h[1]);
      }
      int cur = pq.peek();
      if (prev != cur) {
        ret.add(new int[]{h[0], cur});
        prev = cur;
      }
    }
    return ret;
  }

  public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>(new Comparator<ListNode>() {
      public int compare(ListNode a, ListNode b) {
        return a.val - b.val;
      }
    });
    for (ListNode head : lists) {
      if (head != null) pq.add(head);
    }
    ListNode head = null, tail = null;
    while (!pq.isEmpty()) {
      ListNode temp = pq.poll();
      ListNode next = temp.next;
      temp.next = null;
      if (head == null) {
        head = tail = temp;
      } else {
        tail.next = temp;
        tail = tail.next;
      }
      if (next != null) pq.add(next);
    }
    return head;
  }

  public List<Integer> topKFrequent(int[] array, int k) {
    if (k <= 0) return null;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i : array) {
      if (!map.containsKey(i)) map.put(i, 0);
      map.put(i, map.get(i) + 1);
    }

    List<Integer> ret = new LinkedList<Integer>();
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
      public int compare(Integer a, Integer b) {
        return map.get(a).compareTo(map.get(b));
      }
    });
    for (int i : map.keySet()) {
      if (pq.size() < k) {
        pq.add(i);
      } else {
        if (map.get(i) > map.get(pq.peek())) {
          pq.poll();
          pq.add(i);
        }
      }
    }
    while (!pq.isEmpty()) {
      ret.add(pq.poll());
    }
    return ret;
  }

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

  public String sortByCharFrequency(String s) {
    if (s == null || s.isEmpty()) return s;
    HashMap<Character, Integer> map = new HashMap<Character, Integer>();
    List<Character> l = new ArrayList<Character>();

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!map.containsKey(c)) {
        map.put(c, 0);
        l.add(c);
      }
      map.put(c, map.get(s.charAt(i))+1);
    }
    Collections.sort(l, new Comparator<Character>() {
      public int compare(Character a, Character b) {
        return -map.get(a).compareTo(map.get(b));
      }
    });
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < l.size(); i++) {
      int freq = map.get(l.get(i));
      for (int j = 0; j < freq; j++) {
        sb.append(l.get(i));
      }
    }
    return sb.toString();
  }

  public int[] maxSlidingWindow(int[] array, int k) {
    if (array == null || array.length == 0 || k <= 0) return new int[0];
    int n = array.length;
    int[] ret = new int[n - k + 1];
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());
    for (int i = 0; i < k; i++) pq.add(array[i]);
    int left = 0, right = k-1;
    for (int i = 0; i <= n-k; i++) {
      ret[i] = pq.peek();
      pq.remove(array[left]);
      if (right + 1 <= n-1) pq.add(array[right+1]);
      left++;
      right++;
    }
    return ret;
  }

  public int[] maxSlidingWindowO1(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k <= 0) return new int[0];
    int n = nums.length;
    int[] ret = new int[n-k+1];
    LinkedList<Integer> queue = new LinkedList<Integer>();
    for (int i = 0; i < n; i++) {
      if (!queue.isEmpty() && queue.peekFirst() == i-k) queue.poll(); // remove elements out of bound.
      while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) queue.removeLast(); // put the current number after all numbers greater than it.
      queue.add(i);
      if (i - k +1 >= 0) ret[i-k+1] = nums[queue.peekFirst()];
    }
    return ret;
  }

  public int kthSmallestInMatrixKMerge(int[][] matrix, int k) { // klogn
    if (matrix == null || matrix.length == 0) return Integer.MIN_VALUE;
    int n = matrix.length;
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int[] idx = new int[n];
    for (int i = 0; i < n; i++) pq.add(matrix[i][0]);
    int min = 0;
    for (int count = 1; count <= k; count++) {
      min = pq.poll();
      for (int i = 0; i < n; i++) {
        if (idx[i] < n && matrix[i][idx[i]] == min) {
          idx[i]++;
          if (idx[i] < n) pq.add(matrix[i][idx[i]]);
          break;
        }
      }
    }
    return min;
  }

  public int minMeetingRoom(Interval[] time) {
    if (time == null || time.length == 0) return 0;
    Arrays.sort(time, new Comparator<Interval>() {
      public int compare(Interval a, Interval b) {
        return a.start - b.start;
      }
    });
    PriorityQueue<Integer> finish = new PriorityQueue<>();
    int count = 0;
    for (int i = 0; i < time.length; i++) {
      if (finish.isEmpty()) {
        count++;
        finish.add(time[i].end);
      } else if (time[i].start < finish.peek()) {
        count++;
        finish.add(time[i].end);
      } else {
        finish.poll();
        finish.add(time[i].end);
      }
    }
    return count;
  }

  public List<int[]> findKPairsWithSmallestSumEnum(int[] nums1, int[] nums2, int k) {
    List<int[]> ret = new LinkedList<int[]>();
    if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0) {
      return ret;
    }
    boolean[][] visited = new boolean[nums1.length][nums2.length];
    PriorityQueue<int[]> minheap = new PriorityQueue<int[]>(new Comparator<int[]>() {
      public int compare(int[] a, int[] b) {
        return nums1[a[0]] + nums2[a[1]] - (nums1[b[0]] + nums2[b[1]]);
      }
    });
    minheap.add(new int[] {0, 0});
    visited[0][0] = true;
    while (!minheap.isEmpty() && ret.size() < k) {
      int[] min = minheap.poll();
      int x = min[0];
      int y = min[1];
      ret.add(new int[] {nums1[x], nums2[y]});
      if (x + 1 < nums1.length && !visited[x+1][y])  {
        minheap.add(new int[] {x+1, y});
        visited[x+1][y] = true;
      }
      if (y + 1 < nums2.length && !visited[x][y+1]) {
        minheap.add(new int[] {x, y+1});
        visited[x][y+1] = true;
      }
    }
    return ret;
  }

  public List<int[]> findKPairsWithSmallestSum(int[] nums1, int[] nums2, int k) {
    List<int[]> ret = new LinkedList<int[]>();
    if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0) {
      return ret;
    }
    PriorityQueue<int[]> minheap = new PriorityQueue<int[]>(new Comparator<int[]>() {
      public int compare(int[] a, int[] b) {
        return nums1[a[0]] + nums2[a[1]] - (nums1[b[0]] + nums2[b[1]]);
      }
    });
    minheap.add(new int[] {0, 0});
    while (!minheap.isEmpty() && ret.size() < k) {
      int[] min = minheap.poll();
      int x = min[0];
      int y = min[1];
      ret.add(new int[]{nums1[x], nums2[y]});
      if (y+1 < nums2.length) minheap.add(new int[]{x, y+1});
      if (y == 0 && x+1 < nums1.length) minheap.add(new int[]{x+1, y});
    }
    return ret;
  }

  public static void main(String[] args) {

    Heap test = new Heap();
    int[][] m = {{1, 2},{3, 3}};
    System.out.println(test.kthSmallestInMatrixKMerge(m, 4));
  }
}

class MedianFinder {
  private PriorityQueue<Integer> maxheap;
  private PriorityQueue<Integer> minheap;
  public MedianFinder() {
    maxheap = new PriorityQueue<>(Collections.reverseOrder()); // first half
    minheap = new PriorityQueue<>(); // second half
  }
  // Adds a number into the data structure.
  public void addNum(int num) {
    maxheap.offer(num);
    minheap.offer(maxheap.poll());
    if (maxheap.size() < minheap.size()) maxheap.offer(minheap.poll());
  }

  // Returns the median of current data stream
  public double findMedian() {
    if (maxheap.size() == minheap.size()) {
      return (maxheap.peek() + minheap.peek())/2.0;
    } else {
      return maxheap.peek();
    }
  }
}
