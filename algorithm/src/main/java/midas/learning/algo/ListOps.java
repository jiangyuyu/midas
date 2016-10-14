package midas.learning.algo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import midas.data.ListNode;
import midas.data.RandomListNode;
import midas.data.TreeNode;


public class ListOps {
  public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    ListNode head = null, tail = null;
    PriorityQueue<ListNode> minHeap = new PriorityQueue<ListNode>(new Comparator<ListNode>() {
      public int compare(ListNode a, ListNode b) {
        if (a.val < b.val) return -1;
        else if (a.val == b.val) return 0;
        else return 1;
      }
    });
    for (ListNode l : lists) {
      if (l != null) minHeap.add(l);
    }
    if (minHeap.isEmpty()) return null;
    while (!minHeap.isEmpty()) {
      ListNode min = minHeap.poll();
      if (min.next != null) minHeap.add(min.next);
      if (head == null) {
        head = tail = min;
      } else {
        tail.next = min;
        tail = tail.next;
      }
    }
    return head;
  }

  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    if (l1 == null || l2 == null) return l1 == null? l2 : l1;
    ListNode p = l1, q = l2;
    int sum = 0, carry = 0;
    ListNode head = null, tail = null;
    while (p != null && q != null) {
      sum = p.val + q.val + carry;
      carry = sum /10;
      sum = sum%10;
      if (head == null) {
        head = tail = new ListNode(sum);
      } else {
        tail.next = new ListNode(sum);
        tail = tail.next;
      }
      p = p.next;
      q = q.next;
    }
    if (q != null) p = q;
    while (p != null) {
      sum = p.val + carry;
      carry = sum/10;
      sum = sum%10;
      tail.next = new ListNode(sum);
      tail = tail.next;
      p = p.next;
    }
    if (carry > 0) {
      tail.next = new ListNode(carry);
      tail = tail.next;
    }
    return head;
  }

  public ListNode reverseListIter(ListNode list) {
    if (list == null || list.next == null) return list;
    ListNode newhead = null;
    ListNode cur = list;
    while (cur != null) {
      ListNode temp = cur;
      cur = cur.next;
      temp.next = newhead;
      newhead = temp;
    }
    return newhead;
  }
  public ListNode reverseListRecursive(ListNode list) {
    if (list == null || list.next == null) return list;
    ListNode head = list;
    ListNode newhead = reverseListRecursive(list.next);
    ListNode p = newhead;
    for (; p.next != null; p = p.next);
    p.next = head;
    head.next = null;
    return newhead;
  }

  public ListNode reverseListBetween(ListNode head, int m, int n) {
    if (head == null || m == n) return head;
    ListNode p = head, prep = null;
    int i = 1;
    for (i = 1; i < m; i++) {
      if (p == null) break;
      prep = p;
      p = p.next;
    }
    if (i < m) return head;
    ListNode newhead = null, newtail = null;
    for (; i <= n; i++) {
      if (p == null) break;
      ListNode temp = p;
      p = p.next;
      temp.next = null;
      if (newhead == null) newtail = newhead = temp;
      else {
        temp.next = newhead;
        newhead = temp;
      }
    }
    if (prep == null) {
      head = newhead;
    } else {
      prep.next = newhead;
    }
    newtail.next = p;
    return head;
  }
  public ListNode reverseKGroup(ListNode head, int k) {
    if (head == null || head.next == null || k <= 1) return head;
    int len = 0;
    for (ListNode p = head; p != null; p = p.next) {
      len++;
      if (len == k) break;
    }
    if (len < k) return head;
    ListNode newhead = null, newtail = null;
    ListNode cur = head;
    int i = 0;
    for (i = 0; i < k && cur != null; i++) {
      ListNode temp = cur;
      cur = cur.next;
      if (newhead == null) {
        newtail = temp;
      }
      temp.next = newhead;
      newhead = temp;
    }

    ListNode rest = reverseKGroup(cur, k);
    newtail.next = rest;
    return newhead;
  }
  public void deleteNodeWithOneAccess(ListNode node) {
    node.val = node.next.val;
    node.next = node.next.next;
  }

  public boolean hasCycle(ListNode head) {
    if (head == null) return false;
    ListNode fast = head, slow = head;
    while (fast != null && slow != null) {
      if (fast.next != null) fast = fast.next.next;
      else fast = null;
      slow = slow.next;
      if (fast != null && fast == slow) return true;
    }
    return false;
  }

  public ListNode findCycleStart(ListNode head) {
    if (head == null) return null;
    ListNode fast = head, slow = head;
    while(fast != null) {
      if (fast.next != null) fast = fast.next.next;
      else fast = null;
      slow = slow.next;
      if (fast == slow) break;
    }
    if (fast == null || fast != slow) return null;
    slow = head;
    while (fast != slow) {
      fast = fast.next;
      slow = slow.next;
    }
    return fast;
  }

  public ListNode mergeSortedList(ListNode l1, ListNode l2) {
    if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
    ListNode head = null, tail = null;
    ListNode p = l1, q = l2;
    while (p != null && q != null) {
      if (p.val <= q.val) {
        ListNode temp = p;
        p = p.next;
        temp.next = null;
        if (head == null) {
          head = tail = temp;
        } else {
          tail.next = temp;
          tail = tail.next;
        }
      } else {
        ListNode temp = q;
        q = q.next;
        temp.next = null;
        if (head == null) head = tail = temp;
        else {
          tail.next = temp;
          tail = tail.next;
        }
      }
    }
    if (p != null) tail.next = p;
    else if (q != null) tail.next = q;
    return head;
  }

  public ListNode sortListQuickSort(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode lefthead = null, lefttail = null, righthead = null, righttail = null;
    ListNode p = head.next;
    head.next = null;
    while (p != null) {
      ListNode temp = p;
      p = p.next;
      temp.next = null;
      if (temp.val <= head.val) {
        if (lefthead == null) lefthead = lefttail = temp;
        else {
          lefttail.next = temp;
          lefttail = lefttail.next;
        }
      } else {
        if (righthead == null) righthead = righttail = temp;
        else {
          righttail.next = temp;
          righttail = righttail.next;
        }
      }
    }
    ListNode small = sortListQuickSort(lefthead);
    ListNode large = sortListQuickSort(righthead);
    p = small;
    while (p != null && p.next != null) p = p.next;
    if (p == null) {
      head.next = large;
      return head;
    } else {
      p.next = head;
      head.next = large;
      return small;
    }
  }

  public ListNode sortListMergeSort(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode fast = head, slow = head;
    while (fast != null && fast.next != null && fast.next.next != null) {
      fast = fast.next.next;
      slow = slow.next;
    }
    ListNode l2 = slow.next;
    slow.next = null;
    ListNode l1 = head;
    ListNode h1 = sortListMergeSort(l1);
    ListNode h2 = sortListMergeSort(l2);
    return mergeSortedList(h1, h2);
  }

  public ListNode getIntersectionNode(ListNode h1, ListNode h2) {
    if (h1 == null || h2 == null) return null;
    int len1 = 0, len2 = 0;
    ListNode p = h1;
    while (p != null) {
      len1++;
      p = p.next;
    }
    p = h2;
    while (p != null) {
      len2++;
      p = p.next;
    }
    int diff = len1 > len2? len1-len2: len2 - len1;
    if (len1 > len2) p = h1;
    else p = h2;
    for (int i = 0; i < diff; i++) p = p.next;
    ListNode q = len1 > len2? h2 : h1;
    while (p != null && q != null && p != q) {
      p = p.next;
      q = q.next;
    }
    if (p != null && p == q) return p;
    return null;

  }

  public RandomListNode copyRandomList(RandomListNode head) {
    if (head == null) return null;
    HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
    RandomListNode p = head;
    RandomListNode newhead = null, newtail = null;
    while (p != null) {
      if (map.containsKey(p)) {
        newtail.next = map.get(p);
        newtail = newtail.next;
      } else {
        RandomListNode temp = new RandomListNode(p.label);
        map.put(p, temp);
        if (newhead == null) {
          newhead = newtail = temp;
        } else {
          newtail.next = temp;
          newtail = newtail.next;
        }
      }

      if (p.random != null) {
        if (map.containsKey(p.random)) {
          newtail.random = map.get(p.random);
        } else {
          RandomListNode r = new RandomListNode(p.random.label);
          map.put(p.random, r);
          newtail.random = r;
        }
      }
      p = p.next;
    }
    return newhead;
  }

  public ListNode removeNthToEnd(ListNode head, int n) {
    if (head == null) return null;
    ListNode fast = head, slow = head;
    int i = 0;
    for (i = 0; i < n && fast != null; i++) {
      fast = fast.next;
    }
    if (i < n) return head;
    ListNode prev = null;
    while (fast != null) {
      fast = fast.next;
      prev = slow;
      slow = slow.next;
    }
    if (prev == null) head = slow.next;
    else {
      prev.next = slow.next;
      slow.next = null;
    }
    return head;
  }

  public boolean isPalinddromeList(ListNode head) {
    if (head == null || head.next == null) return true;
    int len = 0;
    ListNode p = head;
    while (p != null) {
      len++;
      p = p.next;
    }
    int firstLen = len/2;
    p = head;
    ListNode newhead = null;
    for (int i = 0; i < firstLen; i++) {
      ListNode temp = p;
      p = p.next;
      temp.next = newhead;
      newhead = temp;
    }
    if (len%2 == 1) p = p.next;
    ListNode q = newhead;
    while (p != null && q != null) {
      if (p.val != q.val) return false;
      p = p.next;
      q = q.next;
    }
    if (p != null || q != null) return false;
    return true;
  }

  public boolean isPalindromeListFastSlow(ListNode head) {
    if (head == null || head.next == null) return true;
    ListNode fast = head, slow = head, prev = null;
    while (fast != null && fast.next != null) {
      fast = fast.next.next;
      prev = slow;
      slow = slow.next;
    }
    prev.next = null;
    if (fast != null) {
      slow = slow.next;
    }
    ListNode newhead = null;
    while (slow != null) {
      ListNode cur = slow;
      slow = slow.next;
      cur.next = newhead;
      newhead = cur;
    }
    slow = newhead;
    fast = head;
    while(fast != null && slow != null) {
      if (fast.val != slow.val) return false;
      fast = fast.next;
      slow = slow.next;
    }
    return true;
  }

  public ListNode insertionSortList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newhead = null, p = head;
    while (p != null) {
      ListNode temp = p;
      p = p.next;
      temp.next = null;
      if (newhead == null) {
        newhead = temp;
      } else {
        ListNode cur = newhead, prev = null;
        while (cur != null && cur.val < temp.val) {
          prev = cur;
          cur = cur.next;
        }
        if (prev == null) {
          temp.next = newhead;
          newhead = temp;
        } else {
          temp.next = cur;
          prev.next = temp;
        }
      }
    }
    return newhead;
  }

  public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode p = head, q = head.next, t = head.next.next;
    ListNode rest = swapPairs(t);
    q.next = p;
    p.next = rest;
    head = q;
    return head;
  }

  public ListNode removeDuplicateSortedListLeaveOneCopy(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode left = head, right = head.next;
    while (right != null) {
      if (right.val != left.val) {
        left = left.next;
        right = right.next;
      } else {
        while (right != null && right.val == left.val) right = right.next;
        left.next = right;
      }
    }
    return head;
  }
  public ListNode removeDuplicateFromSortedNoCopy(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode prev = null, left = head, right = head.next;
    while (right != null) {
      if (right.val != left.val) {
        prev = left;
        left = left.next;
        right = right.next;
      } else {
        while (right != null && right.val == left.val) right = right.next;
        if (prev == null) {
          left = right;
          head = right;
          if (right != null) right = right.next;
        } else {
          left = prev;
          prev.next = right;
        }
      }
    }
    return head;
  }

  public ListNode reorderList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode fast = head, slow = head, prev = null;
    while (fast != null && fast.next != null) {
      fast = fast.next.next;
      prev = slow;
      slow = slow.next;
    }
    ListNode newhead = null;
    if (fast != null) {
      prev = slow;
      slow = slow.next;
    }
    if (prev != null) prev.next = null;
    while (slow != null) {
      ListNode cur = slow;
      slow = slow.next;
      cur.next = newhead;
      newhead = cur;

    }
    ListNode p = head, q = newhead;
    while(q != null) {
      ListNode temp = q;
      q = q.next;
      temp.next = p.next;
      p.next = temp;
      p = temp.next;
    }
    return head;
  }

  public ListNode oddEvenList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode odd = null, oddtail = null, even = null, eventail = null;
    int count = 1;
    ListNode p = head;
    while (p != null) {
      ListNode temp = p;
      p = p.next;
      temp.next = null;
      if (count %2 == 1) {
        if (odd == null) odd = oddtail = temp;
        else {
          oddtail.next = temp;
          oddtail = oddtail.next;
        }
      } else {
        if (even == null) {
          even = eventail = temp;
        } else {
          eventail.next = temp;
          eventail = eventail.next;
        }
      }
      count++;
    }
    oddtail.next = even;
    return odd;
  }

  public ListNode removeElementFromList(ListNode head, int val) {
    if (head == null) return null;
    ListNode prev = null, p = head;
    while (p != null) {
      if (p.val == val) {
        if (prev == null) {
          head = p.next;
          ListNode temp = p;
          p = p.next;
          temp.next = null;
        } else {
          prev.next = p.next;
          ListNode temp = p;
          p = p.next;
          temp.next = null;
        }
      } else {
        prev = p;
        p = p.next;
      }
    }
    return head;
  }

  public ListNode rotateRight(ListNode head, int k) {
    if (head == null || k == 0) return head;
    int len = 0;
    for (ListNode p = head; p != null; p = p.next) len++;
    k = k % len;
    if (k == 0) return head;
    ListNode prevp = null, p = head, prevq = null, q = head;
    for (int i = 0; i < k; i++) {
      q = q.next;
    }
    while (q != null) {
      prevp = p;
      p = p.next;
      prevq = q;
      q = q.next;
    }
    if (prevp != null) prevp.next = null;
    if (prevq != null) prevq.next = head;
    head = p;
    return head;
  }
  public ListNode listPlusOne(ListNode head) {
    if (head == null) return head;
    ListNode newhead = reverseListIter(head);
    ListNode prev = null;
    int carry = 1, sum = 0;
    for (ListNode p = newhead; p != null; prev = p, p = p.next) {
      sum = p.val + carry;
      carry = sum/10;
      sum = sum % 10;
      p.val = sum;
    }
    if (carry > 0) {
      prev.next = new ListNode(carry);
    }
    return reverseListIter(newhead);
  }
  public ListNode listPlusOneRec(ListNode head) {
    if (head == null) return head;
    int carry = listPlusOneHelper(head, 1);
    if (carry > 0) {
      ListNode newhead = new ListNode(carry);
      newhead.next = head;
      head = newhead;
    }
    return head;
  }
  public int listPlusOneHelper(ListNode head, int carry) {
    if (head == null) return 0;
    if (head.next == null) {
      int sum = head.val + carry;
      carry = sum/10;
      sum = sum % 10;
      head.val = sum;
      return carry;
    }
    carry = listPlusOneHelper(head.next, 1);
    if (carry == 0) return 0;
    int sum = head.val + carry;
    carry = sum/10;
    head.val = sum % 10;
    return carry;
  }
  public ListNode listPlusOneFast(ListNode head) {
    if (head == null) return null;
    ListNode cur = head, rightmost = null;
    while (cur != null) {
      if (cur.val != 9) rightmost = cur;
      cur = cur.next;
    }
    if (rightmost == null) {
      ListNode newhead = new ListNode(1);
      cur = head;
      while (cur != null) {
        cur.val = 0;
        cur = cur.next;
      }
      newhead.next = head;
      head = newhead;
    } else {
      rightmost.val = rightmost.val+1;
      cur = rightmost.next;
      while (cur != null) {
        cur.val = 0;
        cur = cur.next;
      }
    }
    return head;
  }

  public TreeNode sortedListToBST(ListNode head) {
    if (head == null) return null;
    int len = 0;
    for (ListNode p = head; p != null; p = p.next) len++;
    return constructTreeHepler(head, len);
  }
  private TreeNode constructTreeHepler(ListNode head, int len) {
    if (head == null || len == 0) return null;
    if (head != null && len == 1) {
      return new TreeNode(head.val);
    }
    int half = len/2;
    ListNode p = head, prev = null;
    for (int i = 0; i < half; i++) {
      p  = p.next;
    }
    if (prev != null) prev.next = null;
    TreeNode root = new TreeNode(p.val);
    root.left = constructTreeHepler(head, half);
    root.right = constructTreeHepler(p.next, len-half-1);
    return root;
  }

  public ListNode partition(ListNode head, int x) {
    if (head == null || head.next == null) return head;
    ListNode bighead = null, bigtail = null;
    ListNode cur = head, prev = null;
    while (cur != null) {
      if (cur.val < x) {
        prev = cur;
        cur = cur.next;
      }
      else {
        ListNode temp = cur;
        cur = cur.next;
        if (prev != null) prev.next = cur;
        else {
          head = cur;
        }
        temp.next = null;
        if (bighead == null) {
          bighead = bigtail = temp;
        } else {
          bigtail.next = temp;
          bigtail = bigtail.next;
        }
      }
    }
    if (prev != null) {
      prev.next = bighead;
    } else {
      head = bighead;
    }
    return head;
  }



  public static void main(String[] args) {
    ListNode head = new ListNode(7);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    ListOps test = new ListOps();
    head = test.reverseKGroup(head, 2);
    for (ListNode p = head; p!= null; p = p.next) {
      System.out.println(p.val);
    }
  }
}

class PhoneDirectory {
  private LinkedList<Integer> available;
  private HashSet<Integer> used;
  /** Initialize your data structure here
   @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
  public PhoneDirectory(int maxNumbers) {
    for (int i = 0; i < maxNumbers; i++) {
      available.add(i);
    }
  }

  /** Provide a number which is not assigned to anyone.
   @return - Return an available number. Return -1 if none is available. */
  public int get() {
    if (available.isEmpty()) return -1;
    int assigned = available.get(0);
    used.add(assigned);
    available.remove(0);
    return assigned;
  }

  /** Check if a number is available or not. */
  public boolean check(int number) {
    return !used.contains(number);
  }

  /** Recycle or release a number. */
  public void release(int number) {
    if (used.contains(number)) {
      used.remove(number);
      available.add(number);
    }
  }
}
