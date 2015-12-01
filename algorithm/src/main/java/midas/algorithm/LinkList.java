package midas.algorithm;

/**
 * Created by xli1 on 11/6/15.
 */

class ListNode {
  int val;
  ListNode next;
  ListNode(int x) { val = x; }
}

public class LinkList {
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    if (l1 == null || l2 == null) {
      return l1 == null? l2 : l1;
    }
    ListNode p = l1;
    ListNode q = l2;
    ListNode head = null, tail = null;
    int carry = 0;
    while(p != null && q != null) {
      int sum = p.val + q.val + carry;
      carry = sum/10;
      int val = sum%10;
      if (head == null) {
        head = new ListNode(val);
        tail = head;
      } else {
        tail.next = new ListNode(val);
        tail = tail.next;
      }
      p = p.next;
      q = q.next;
    }
    if (p == null && q == null) {
      if (carry != 0) {
        tail.next = new ListNode(carry);
      }
    } else {
      if (q != null) p = q;
      if (carry == 0) tail.next = p;
      else {
        while (carry != 0 && p != null) {
          int sum = carry + p.val;
          carry = sum/10;
          tail.next = new ListNode(sum%10);
          tail = tail.next;
          p = p.next;
        }
        if (carry == 0) {
          tail.next = p;
        } else {
          tail.next = new ListNode(carry);
        }
      }
    }
    return head;
  }

  public ListNode removeNthFromEnd(ListNode head, int n) {
    if (head == null || n == 0) return head;
    ListNode prev = null, cur = head, next = head;
    int i = 0;
    for (i = 0; i < n && next != null; i++) {
      next = next.next;
    }
    if (i < n) return head;
    while (next != null) {
      prev = cur;
      cur = cur.next;
      next = next.next;
    }
    if (prev != null) {
      prev.next = cur.next;
      cur.next = null;
    } else {
      head = cur.next;
      cur.next = null;
    }
    return head;
  }

  public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode p = head, q = head.next, r = q.next, prev = null;
    while (p != null && q != null) {
      q.next = p;
      p.next = r;
      if (prev == null) {
        head = q;
      } else {
        prev.next = q;
      }
      prev = p;
      p = r;
      if (r != null) q = r.next;
      else q = null;
      if (q != null) r = q.next;
      else r = null;
    }
    return head;
  }

  public static void main(String[] args) {
    LinkList test = new LinkList();
    ListNode head = null, cur = null;
    for (int i = 0; i < 5; i++) {
      if (head == null) {
        head = new ListNode(i);
        cur = head;
      } else {
        cur.next = new ListNode(i);
        cur = cur.next;
      }
    }
    head = test.removeNthFromEnd(head, 2);
    cur = head;
    while (cur != null) {
      System.out.println(cur.val);
      cur = cur.next;
    }
  }
}
