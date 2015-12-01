package midas.algorithm;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


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

  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
    ListNode head = null, tail = null, p = l1, q = l2;
    while (p != null && q != null) {
      if (p.val < q.val) {
        if (head == null) {
          head = tail = p;
          p = p.next;
          tail.next = null;
        } else {
          tail.next = p;
          p = p.next;
          tail = tail.next;  //bug here
          tail.next = null;
        }
      } else {
        if (head == null) {
          head = tail = q;
          q = q.next;
          tail.next = null;
        } else {
          tail.next = q;
          q = q.next;
          tail = tail.next;   //bug here
          tail.next = null;
        }
      }
    }
    if (p != null) {
      tail.next = p;
    } else if (q != null) {
      tail.next = q;
    }
    return head;
  }

  public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    ListNode head = null, tail = null;
    int N = lists.length;
    PriorityQueue<ListNode> pq = new PriorityQueue<ListNode>(N, new Comparator<ListNode>() {
      @Override
      public int compare(ListNode o1, ListNode o2) {
        return o2.val - o1.val;
      }
    });
    for (ListNode l : lists) {
      if (l != null) pq.add(l);
    }
    while (!pq.isEmpty()) {
      ListNode cur = pq.poll();
      if (head == null) {
        head = tail = cur;
        cur = cur.next;
        tail.next = null;
        if (cur != null) pq.add(cur);
      } else {
        tail.next = cur;
        cur = cur.next;
        tail = tail.next;
        tail.next = null;
        if (cur != null) pq.add(cur);
      }
    }
    return head;
  }

  public int findNthLargest(List<Integer> num, int N) {
    if (num == null || num.size() < N) throw new IllegalArgumentException();
    PriorityQueue<Integer> pq = new PriorityQueue<>(N); // minheap by default
    for (Integer a : num) {
      if (pq.size() < N) {
        pq.add(a);
      } else {
        if (a > pq.peek()) {
          pq.remove();
          pq.add(a);
        }
      }
    }
    return pq.peek();
  }

  public boolean hasIntegerSquareRoot(int n) {
    if (n < 0) return false;
    int left = 0, right = n;
    while (left < right) {
      int mid = left + (right - left)/2;
      if (mid * mid == n) {
        return true;
      } else if (mid * mid < n) {
        left = mid+1;
      } else {
        right = mid-1;
      }
    }
    if (left * left == n || right * right == n) return true;
    return false;
  }

  public static void main(String[] args) {
    Sort test = new Sort();
    Integer[] arr = {2, 5, 12, 4, 6, 8};
    System.out.println(test.hasIntegerSquareRoot(8));

  }
}
