package midas.learning.algo;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import midas.data.TreeNode;

public class Tree {
  public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
  }

  public boolean isSameTree(TreeNode r1, TreeNode r2) {
    if (r1 == null && r2 == null) return true;
    if (r1 == null || r2 == null) return false;
    return r1.val == r2.val && isSameTree(r1.left, r2.left) && isSameTree(r1.right, r2.right);
  }

  public boolean isBalanced(TreeNode root) {
    return isBalancedHelper(root) >= 0;
  }
  public int isBalancedHelper(TreeNode root) {
    if (root == null) return 0;
    int left = isBalancedHelper(root.left);
    int right = isBalancedHelper(root.right);
    int height = Math.max(Math.abs(left), Math.abs(right)) + 1;
    if (left >= 0 && right >= 0 && Math.abs(left-right) <= 1) return height;
    return -height;
  }

  public List<List<Integer>> levelOrderTraversal(TreeNode root) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (root == null) return ret;
    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
    LinkedList<TreeNode> queue2 = new LinkedList<TreeNode>();
    queue.add(root);
    while (!queue.isEmpty()) {
      List<Integer> curLevel = new LinkedList<Integer>();
      while(!queue.isEmpty()) {
        TreeNode p = queue.poll();
        if (p.left != null) queue2.add(p.left);
        if (p.right != null) queue2.add(p.right);
        curLevel.add(p.val);
      }
      ret.add(curLevel);
      queue.addAll(queue2);
      queue2.clear();
    }
    return ret;
  }

  public List<List<Integer>> levelOrderRec(TreeNode root) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (root == null) return ret;
    List<Integer> top = new LinkedList<Integer>();
    top.add(root.val);
    ret.add(top);
    List<List<Integer>> l = levelOrderRec(root.left);
    List<List<Integer>> r = levelOrderRec(root.right);
    int i = 0;
    for (i = 0; i < l.size() && i < r.size(); i++) {
      List<Integer> cur = l.get(i);
      cur.addAll(r.get(i));
      ret.add(cur);
    }
    while (i < l.size()) {
      ret.add(l.get(i++));
    }
    while (i < r.size()) {
      ret.add(r.get(i++));
    }
    return ret;
  }

  public List<List<Integer>> levelOrderBottomUpRec(TreeNode root) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (root == null) return ret;
    List<List<Integer>> l = levelOrderBottomUpRec(root.left);
    List<List<Integer>> r = levelOrderBottomUpRec(root.right);
    int i = 0;
    for (i = 0; i < l.size() && i < r.size(); i++) {
      List<Integer> cur = l.get(l.size() - i - 1);
      cur.addAll(r.get(r.size()-1 - i));
      ret.add(0, cur);
    }
    while (i < l.size()) {
      ret.add(0, l.get(l.size()-1-i));
      i++;
    }
    while (i < r.size()) {
      ret.add(0, r.get(r.size()-1 - i));
      i++;
    }
    List<Integer> top = new LinkedList<Integer>();
    top.add(root.val);
    ret.add(top);
    return ret;
  }

  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (root == null) return ret;
    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
    LinkedList<TreeNode> queue2 = new LinkedList<TreeNode>();
    queue.add(root);
    boolean flip = false;
    while (!queue.isEmpty()) {
      List<Integer> level = new LinkedList<Integer>();
      while(!queue.isEmpty()) {
        TreeNode p = queue.poll();
        if (p.left != null) queue2.add(p.left);
        if (p.right != null) queue2.add(p.right);
        if (!flip) {
          level.add(p.val);
        } else {
          level.add(0, p.val);
        }
      }
      ret.add(level);
      queue.addAll(queue2);
      queue2.clear();
      flip = !flip;
    }
    return ret;
  }

  // It is trivial to do the recursive one.
  public List<Integer> preorder(TreeNode root) {
    List<Integer> ret = new LinkedList<Integer>();
    if (root == null) return ret;
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode p = root;
    while (p != null || !stack.isEmpty()) {
      if (p != null) {
        ret.add(p.val);
        stack.push(p);
        p = p.left;
      } else {
        while (!stack.isEmpty()) {
          TreeNode cur = stack.pop();
          if (cur.right != null) {
            p = cur.right;
            break;
          }
        }
      }
    }
    return ret;
  }

  public List<Integer> inorder(TreeNode root) {
    List<Integer> ret = new LinkedList<Integer>();
    if (root == null) {
      return ret;
    }
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode p = root;
    while (p != null || !stack.isEmpty()) {
      if (p != null) {
        stack.push(p);
        p = p.left;
      } else {
        p = stack.pop();
        ret.add(p.val);
        p = p.right;
      }
    }
    return ret;
  }

  public List<Integer> postorder(TreeNode root) {
    List<Integer> ret = new LinkedList<Integer>();
    if (root == null) return ret;
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode p = root;
    while (p != null || !stack.isEmpty()) {
      if (p != null) {
        // search through the left, but put right child(if available) and root to stack
        if (p.right != null) {
          stack.push(p.right);
        }
        stack.push(p);
        p = p.left;
      } else {
        p = stack.pop();
        if (p.right != null && !stack.isEmpty() && p.right == stack.peek()) {
          // the traceback node has right child, process the right child first
          TreeNode temp = p;
          p = stack.pop();
          stack.push(temp);
        } else {
          ret.add(p.val);
          p = null;
        }
      }
    }
    return ret;
  }

  public TreeNode invert(TreeNode root) {
    if (root == null) return null;
    TreeNode left = invert(root.left);
    TreeNode right = invert(root.right);
    root.left = right;
    root.right = left;
    return root;
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(5);
    Tree testTree = new Tree();
    System.out.println(testTree.postorder(root));
  }
}
