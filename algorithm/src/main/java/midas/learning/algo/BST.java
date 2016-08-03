package midas.learning.algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import midas.data.TreeNode;


public class BST {
  public int closestValueInBST(TreeNode root, double target) {
    double mindiff = Double.MAX_VALUE;
    TreeNode cur = root;
    int res = 0;
    while(cur != null) {
      if (Math.abs(cur.val - target) < 1e-5) return cur.val;
      if (mindiff > Math.abs(target-cur.val)) {
        mindiff =  Math.abs(target-cur.val);
        res = cur.val;
      }
      if (target > cur.val) {
        cur = cur.right;
      } else if (target < cur.val) {
        cur = cur.left;
      }
    }
    return res;
  }
  class Pair {
    double diff;
    int val;
    public Pair(double d, int v) {
      diff = d;
      val = v;
    }
  }
  public List<Integer> closestKValues(TreeNode root, double target, int k) {
    if (root == null) return null;
    //Init a maxheap
    PriorityQueue<Pair> maxheap = new PriorityQueue<Pair>(k, new Comparator<Pair>() {
      public int compare(Pair a, Pair b) {
        if (a.diff < b.diff) return 1;
        else if (a.diff > b.diff) return -1;
        return 0;
      }
    });
    inorder(root, maxheap, target, k);
    List<Integer> ret = new ArrayList<Integer>();
    while(!maxheap.isEmpty()) {
      ret.add(maxheap.poll().val);
    }
    return ret;
  }
  private void inorder(TreeNode root, PriorityQueue<Pair> maxheap, double target, int k) {
    if (root == null) return;
    inorder(root.left, maxheap, target, k);
    if (maxheap.size() < k) {
      maxheap.add(new Pair(Math.abs(root.val - target), root.val));
    } else {
      if (maxheap.peek().diff > Math.abs(root.val - target)) {
        maxheap.poll();
        maxheap.add(new Pair(Math.abs(root.val - target), root.val));
      }
    }
    inorder(root.right, maxheap, target, k);
  }

  
}
