package midas.learning.algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import midas.data.TreeNode;

public class BST {

  public int uniqueBST(int n) {
    if (n == 0) throw new IllegalArgumentException("wrong input");
    if (n == 1) return 1;
    int[] f = new int[n+1]; // f[i] give i numbers, how many bst generated?
    f[0] = 1;
    f[1] = 1;
    for (int i = 2; i <= n; i++) {
      for (int j = 1; j <= i; j++) {
        f[i] += f[j-1] * f[i-j]; //f(1, j-1) and f(j+1, i)
      }
    }
    return f[n];
  }

  public List<TreeNode> generateUniqueBST(int start, int end) {
    List<TreeNode> ret = new ArrayList<TreeNode>();
    if (start > end) return ret;
    if (start == end) {
      TreeNode root = new TreeNode(start);
      ret.add(root);
    } else {
      for (int i = start; i <= end; i++) {
        List<TreeNode> left = generateUniqueBST(start, i-1);
        List<TreeNode> right = generateUniqueBST(i+1, end);
        if (left.isEmpty() && right.isEmpty()) {
          ret.add(new TreeNode(i));
        } else if (left.isEmpty() || right.isEmpty()) {
          if (left.isEmpty()) {
            for (TreeNode r : right) {
              TreeNode root = new TreeNode(i);
              root.right = r;
              ret.add(root);
            }
          } else {
            for (TreeNode l : left) {
              TreeNode root = new TreeNode(i);
              root.left = l;
              ret.add(root);
            }
          }
        } else {
          for (TreeNode l : left) {
            for (TreeNode r: right) {
              TreeNode root = new TreeNode(i);
              root.left = l;
              root.right = r;
              ret.add(root);
            }
          }
        }
      }
    }
    return ret;
  }

  public int kthInBST(TreeNode root, int k) { // error handling?
    if (root == null) throw new IllegalArgumentException();
    TreeNode p = root;
    Stack<TreeNode> stack = new Stack<TreeNode>();
    int i = 0;
    while (p != null || !stack.isEmpty()) {
      if (p != null) {
        stack.push(p);
        p = p.left;
      } else {
        p = stack.pop();
        i++;
        if (i == k) return p.val;
        p = p.right;
      }
    }
    return Integer.MAX_VALUE;
  }


  public boolean isValidBST(TreeNode root) {
    return validBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }
  private boolean validBST(TreeNode root, long min, long max) {
    // suppose do not allow duplicate values
    if (root == null) return true;
    if (root.val < min || root.val > max) return false;
    return validBST(root.left, min, (long)root.val) && validBST(root.right, (long)root.val+1, max);
  }

  public boolean isValidBSTInorder(TreeNode root) {
    if (root == null) return true;
    List<Integer> l = new ArrayList<Integer>();
    inorder(root, l);
    for (int i = 1; i < l.size(); i++) {
      if (l.get(i) <= l.get(i-1)) return false;
    }
    return true;
  }
  private void inorder(TreeNode root, List<Integer> traversal) {
    if (root == null) return;
    inorder(root.left, traversal);
    traversal.add(root.val);
    inorder(root.right, traversal);
  }

  TreeNode pre = null;
  public boolean isValidBSTRecursive(TreeNode root) {
    if (root == null) return true;
    if (!isValidBSTRecursive(root.left)) return false;
    if (pre != null && pre.val >= root.val) return false;
    pre = root;
    if (!isValidBSTRecursive(root.right)) return false;
    return true;
  }


  public TreeNode sortedtArrayToBST(int[] array) {
    return convert(array, 0, array.length - 1);
  }
  private TreeNode convert(int[] array, int start, int end) {
    if (start > end) return null;
    int mid = start + (end - start)/2;
    TreeNode root = new TreeNode(array[mid]);
    root.left = convert(array, start, mid - 1);
    root.right = convert(array, mid+1, end);
    return root;
  }

  public TreeNode LCAinBST(TreeNode root, TreeNode left, TreeNode right) {
    if (root == null) return null;
    if (left == null || right == null) return left == null? right : left;
    if (root == left || root == right) return root;
    if (root.val < left.val && root.val < right.val) return LCAinBST(root.right, left, right);
    else if (root.val > left.val && root.val > right.val) return LCAinBST(root.left, left, right);
    return root;
  }

  public void recoverBST(TreeNode root) {
    TreeNode pre = null, cur = root, first = null, second = null;
    Stack<TreeNode> stack = new Stack<TreeNode>();
    while (cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.push(cur);
        cur = cur.left;
      } else {
        cur = stack.pop();
        if (pre != null) {
          if (pre.val > cur.val) {
            if (first == null) {
              first = pre;
              second = cur;
            } else {
              second = cur;
            }
          }
        }
        pre = cur;
        cur = cur.right;
      }
    }
    if (first != null) {
      int temp = first.val;
      first.val = second.val;
      second.val = temp;
    }
  }

  public TreeNode inorderSuccessorIter(TreeNode root, TreeNode p) {
    TreeNode suc = null, cur = root;
    while(cur != null) { // try to find a node whose value is bigger than p. once find it out, keeps going to the left
      if (cur.val > p.val) {
        suc = cur;
        cur = cur.left;
      } else {
        cur = cur.right;
      }
    }
    return suc;
  }

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

  public int closetValue(TreeNode root, double target) {
    int ret = root.val;
    TreeNode cur = root;
    while (cur != null) {
      if (Math.abs(ret - target) > Math.abs(cur.val-target)) {
        ret = cur.val;
      }
      cur = cur.val < target ? cur.right : cur.left;
    }
    return ret;
  }

  public List<Integer> closestKValues(TreeNode root, double target, int k) {
    List<Integer> ret = new LinkedList<Integer>();
    if (root == null) return ret;
    PriorityQueue<Double> pq = new PriorityQueue<Double>(new Comparator<Double>() {
      public int compare(Double a, Double b) {
        if (Math.abs(a) < Math.abs(b)) return 1;
        else if (Math.abs(a) > Math.abs(b)) return -1;
        return 0;
      }
    });
    TreeNode cur = root;
    Stack<TreeNode> stack = new Stack<TreeNode>();
    while (cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.push(cur);
        cur = cur.left;
      } else {
        cur = stack.pop();
        if (pq.size() < k) pq.add(cur.val - target);
        else {
          if (Math.abs(cur.val - target) < Math.abs(pq.peek())) {
            pq.poll();
            pq.add(cur.val - target);
          }
        }
        cur = cur.right;
      }
    }
    for(Double d : pq) {
      ret.add((int)(target + d));
    }
    return ret;
  }
  public List<Integer> closetKValuesRecur(TreeNode root, double target, int k) {
    List<Integer> ret = new LinkedList<Integer>();
    if (root == null) return ret;
    PriorityQueue<Double> pq = new PriorityQueue<Double>(new Comparator<Double>() {
      public int compare(Double a, Double b) {
        if (Math.abs(a) < Math.abs(b)) return 1;
        else if (Math.abs(a) > Math.abs(b)) return -1;
        return 0;
      }
    });
    inorder(root, pq, target, k);
    for(Double d : pq) {
      ret.add((int)(target + d));
    }
    return ret;
  }
  private void inorder(TreeNode root, PriorityQueue<Double> pq, double target, int k) {
    if (root == null) return;
    inorder(root.right, pq, target, k);
    if (pq.size() < k) {
      pq.add(root.val - target);
    } else if (Math.abs(root.val - target) < pq.peek()) {
      pq.poll();
      pq.add(root.val - target);
    }
    inorder(root.left, pq, target, k);
  }

  public boolean validPreorderBST(int[] array) {
    if (array == null || array.length == 0) return true;
    return validPreorderBST(array, 0, array.length-1);
  }
  private boolean validPreorderBST(int[] arr, int start, int end) {
    if (start > end) return true;
    int pivot = arr[start];
    boolean smaller = false;
    int i = start + 1;
    while (i <= end && arr[i] <= arr[start]) i++;
    int divider = i -1;
    while (i <= end) {
      if (arr[i] < arr[start]) return false;
      i++;
    }
    return validPreorderBST(arr, start+1, divider) && validPreorderBST(arr, divider+1, end);
  }

  public int largestBSTSubtree(TreeNode root) {
    int[] minMaxTotal = new int[2];
    minMaxTotal[0] = Integer.MIN_VALUE;
    minMaxTotal[1] = Integer.MAX_VALUE;
    int max = largestBSTUtil(root, minMaxTotal);
    return Math.abs(max);
  }
  private int largestBSTUtil(TreeNode root, int[] minMax) {
    if (root == null) return 0;
    int[] leftMinMax = new int[2];
    int[] rightMinMax = new int[2];
    leftMinMax[0] = Integer.MIN_VALUE; leftMinMax[1] = Integer.MAX_VALUE;
    rightMinMax[0] = Integer.MIN_VALUE; rightMinMax[1] = Integer.MAX_VALUE;
    int left = largestBSTUtil(root.left, leftMinMax);
    int right = largestBSTUtil(root.right, rightMinMax);
    if (left >= 0 && right >= 0) {
      if ((root.left == null || root.val >= leftMinMax[1]) && (root.right == null || root.val < rightMinMax[0])) {
        int total = left + right + 1;
        minMax[0] = root.left == null? root.val:leftMinMax[0];
        minMax[1] = root.right == null? root.val:rightMinMax[1];
        return total;
      }
    }
    int total = Math.max(Math.abs(left), Math.abs(right));
    return -total;
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(0);
    BST test = new BST();
  }
}

class BSTIterator implements Iterator<Integer> {
  TreeNode root;
  Stack<TreeNode> stack;
  TreeNode cur;
  public BSTIterator(TreeNode root) {
    this.root = root;
    stack = new Stack<TreeNode>();
    cur = root;
    while (cur != null) {
      stack.push(cur);
      cur = cur.left;
    }
  }
  public boolean hasNext() {
    return !stack.isEmpty();
  }

  public Integer next() {
    TreeNode p = stack.pop();
    int ret = p.val;
    if (p.right != null) {
      p = p.right;
      while (p != null) {
        stack.push(p);
        p = p.left;
      }
    }
    return ret;
  }
}
