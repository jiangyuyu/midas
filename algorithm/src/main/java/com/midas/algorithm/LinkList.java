package com.midas.algorithm;

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
}
