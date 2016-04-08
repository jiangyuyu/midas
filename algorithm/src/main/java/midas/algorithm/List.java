package midas.algorithm;

import midas.data.ListNode;


public class List {
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
    ListNode head = null, tail = null, p = l1, q = l2;
    int sum = 0, carry = 0;
    while (p != null && q != null) {
      sum = p.val + q.val + carry;
      ListNode n = new ListNode(sum % 10);
      carry = sum / 10;
      if (head == null) {
        head = tail = n;
      } else {
        tail.next = n;
        tail = tail.next;
      }
      p = p.next;
      q = q.next;
    }
    if (q != null) {
      p = q;
    }
    while (p != null && carry != 0) {
      sum = p.val + carry;
      ListNode n = new ListNode(sum%10);
      carry = sum/10;
      tail.next = n;
      tail = tail.next;
      p = p.next;
    }
    if (p != null) {
      tail.next = p;
    } else if (carry != 0) {
      tail.next = new ListNode(carry);
    }
    return head;
  }

}
