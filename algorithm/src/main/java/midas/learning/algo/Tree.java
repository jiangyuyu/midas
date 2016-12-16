package midas.learning.algo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import midas.data.TreeLinkNode;
import midas.data.TreeNode;

public class Tree {
  public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
  }

  public int minDepth(TreeNode root) {
    if (root == null) return 0;
    if (root.left == null && root.right == null) return 1;
    if (root.left == null || root.right == null) {
      return root.left == null? 1 + minDepth(root.right) : 1 + minDepth(root.left);
    }
    return 1 + Math.min(minDepth(root.left), minDepth(root.right));
  }

  public boolean isSameTree(TreeNode r1, TreeNode r2) {
    if (r1 == null && r2 == null) return true;
    if (r1 == null || r2 == null) return false;
    return r1.val == r2.val && isSameTree(r1.left, r2.left) && isSameTree(r1.right, r2.right);
  }

  public boolean isBalanced(TreeNode root) {
    return isBalancedHelper(root) >= 0;
  }
  private int isBalancedHelper(TreeNode root) {
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

  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> ret = new ArrayList<Integer>();
    if (root == null) return ret;
    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
    LinkedList<TreeNode> queue2 = new LinkedList<TreeNode>();
    queue.add(root);
    ret.add(root.val);
    while (!queue.isEmpty()) {
      while (!queue.isEmpty()) {
        TreeNode p = queue.poll();
        if (p.left != null) queue2.add(p.left);
        if (p.right != null) queue2.add(p.right);
      }
      if (!queue2.isEmpty()) {
        ret.add(queue2.peekLast().val);
      }
      queue.addAll(queue2);
      queue2.clear();
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

  public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);
    root.left = right;
    root.right = left;
    return root;
  }

  public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    return isSymmetric(root.left, root.right);
  }
  private boolean isSymmetric(TreeNode left, TreeNode right) {
    if (left == null && right == null) return true;
    if (left == null || right == null) return false;
    return left.val == right.val && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
  }

  public boolean pathSum(TreeNode root, int sum) {
    if (root == null) return false;
    if (root.left == null && root.right == null) {
      return root.val == sum;
    }
    return pathSum(root.left, sum-root.val) || pathSum(root.right, sum-root.val);
  }

  public List<List<Integer>> findAllPathSum(TreeNode root, int sum) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (root == null) return ret;
    List<List<Integer>> paths = findAllPath(root);
    // filter by sum
    for (List<Integer> path : paths) {
      int s = 0;
      for (Integer v : path) s+=v;
      if (s == sum) {
        ret.add(path);
      }
    }
    return ret;
  }
  private List<List<Integer>> findAllPath(TreeNode root) {
    if (root == null) return new LinkedList<List<Integer>>();
    List<List<Integer>> l = findAllPath(root.left);
    List<List<Integer>> r = findAllPath(root.right);
    l.addAll(r);
    if (l.isEmpty()) {
      List<Integer> list = new LinkedList<Integer>();
      l.add(list);
    }
    for (List<Integer> list : l) {
      list.add(0, root.val);
    }
    return l;
  }

  public int maxPathSum(TreeNode root) {
    if (root == null) return 0;
    int[] max = new int[1];
    max[0] = Integer.MIN_VALUE;
    maxPathSum(root, max);
    return max[0];
  }
  private int maxPathSum(TreeNode root, int[] globalMax) {
    if (root == null) return 0;
    int left = maxPathSum(root.left, globalMax);
    int right = maxPathSum(root.right, globalMax);
    globalMax[0] = Math.max(globalMax[0], root.val + (left > 0? left : 0) + (right>0? right:0));
    return Math.max(root.val, root.val + Math.max(left, right));
  }

  public List<String> binaryTreePaths(TreeNode root) {
    List<String> ret = new ArrayList<String>();
    if (root == null) return ret;
    List<String> left = binaryTreePaths(root.left);
    List<String> right = binaryTreePaths(root.right);
    left.addAll(right);
    if (left.isEmpty()) {
      ret.add(Integer.toString(root.val));
    } else {
      for (String s : left) {
        ret.add(root.val + "->" + s);
      }
    }
    return ret;
  }

  public void connectToRightSibling(TreeLinkNode root) {
    if (root == null) return;
    LinkedList<TreeLinkNode> queue = new LinkedList<TreeLinkNode>();
    LinkedList<TreeLinkNode> queue2 = new LinkedList<TreeLinkNode>();
    queue.add(root);
    while (!queue.isEmpty()) {
      TreeLinkNode pre = null;
      while(!queue.isEmpty()) {
        TreeLinkNode p = queue.poll();
        if (p.left != null) queue2.add(p.left);
        if (p.right != null) queue2.add(p.right);
        if (pre != null) pre.next = p;
        pre = p;
      }
      queue.addAll(queue2);
      queue2.clear();
    }
  }

  public void connectToRightConstantSpace(TreeLinkNode root) {
    TreeLinkNode head = null, tail = null, p = root;
    while (p != null) {
      while (p != null) {
        if (p.left != null) {
          if (head == null) {
            head = tail = p.left;
          } else {
            tail.next = p.left;
            tail = tail.next;
          }
        }
        if (p.right != null) {
          if (head == null) {
            head = tail = p.right;
          } else {
            tail.next = p.right;
            tail = tail.next;
          }
        }
        p = p.next;
      }
      p = head;
      head = null;
      tail = null;
    }
  }

  public TreeNode buildTree(int[] preorder, int[] inorder){
    if (preorder == null || inorder == null || preorder.length != inorder.length) throw new IllegalArgumentException();
    return construct(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
  }
  private TreeNode construct(int[] preorder, int pstart, int pend, int[] inorder, int instart, int inend) {
    if (pstart > pend) return null;
    TreeNode root = new TreeNode(preorder[pstart]);
    int i = 0;
    for ( i = instart; i<= inend; i++) {
      if (inorder[i] == preorder[pstart]) break;
    }
    if (i > inend) throw new IllegalArgumentException();
    root.left = construct(preorder, pstart+1, pstart + (i-instart), inorder, instart, i-1);
    root.right = construct(preorder, pstart + (i-instart)+1, pend, inorder, i+1, inend);
    return root;
  }

  public TreeNode buildTreePost(int[] inorder, int[] postorder) {
    if (inorder == null || postorder == null || inorder.length != postorder.length) return null;
    return constructPost(inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
  }
  private TreeNode constructPost(int[] inorder, int instart, int inend, int[] postorder, int poststart, int postend) {
    if (instart > inend) return null;
    TreeNode root = new TreeNode(postorder[postend]);
    int i = 0;
    for (i = instart; i <= inend; i++) {
      if (inorder[i] == postorder[postend]) break;
    }
    if (i > inend) throw new IllegalArgumentException();
    root.left = constructPost(inorder, instart, i-1, postorder, poststart, poststart+i-instart-1);
    root.right = constructPost(inorder, i+1, inend, postorder, poststart+i-instart, postend-1);
    return root;
  }

  public TreeNode LCA(TreeNode root, TreeNode left, TreeNode right) {
    if (root == null) return null;
    if (left == null || right == null) return left == null ? right : left;
    if (root == left || root == right) return root;
    TreeNode l = LCA(root.left, left, right);
    TreeNode r = LCA(root.right, left, right);
    if (l == null && r == null) return null;
    if (l != null && r != null) return root;
    return l == null? r : l;
  }

  public void flattenTreeToList(TreeNode root) { // move all the nodes to the right
    if (root == null) return;
    flattenTreeToList(root.left);
    flattenTreeToList(root.right);
    TreeNode right = root.right;
    root.right = root.left;
    root.left = null;

    TreeNode p  = root;
    while (p.right != null) {
      p = p.right;
    }
    p.right = right;
  }

  public int countCompleteTree(TreeNode root) {
    if (root == null) return 0;
    int lh = 0, rh= 0;
    TreeNode p = root;
    while (p != null) {
      lh++;
      p = p.left;
    }
    p = root;
    while (p != null) {
      rh++;
      p = p.right;
    }
    if (lh == rh) {
      return (1<<lh) - 1;
    }
    return 1 + countCompleteTree(root.left) + countCompleteTree(root.right);
  }

  public int sumRootToLeaf(TreeNode root) {
    return sumRootToLeafHelper(root, 0);
  }
  private int sumRootToLeafHelper(TreeNode root, int total) {
    if (root == null) return 0; // wrong at first thought
    total = total * 10 + root.val;
    if (root.left == null && root.right == null) return total;
    return sumRootToLeafHelper(root.left, total) + sumRootToLeafHelper(root.right, total);
  }

  public int houseRobber(TreeNode root) {
    int[] ret = new int[2]; // max total without root, max total with root.
    houseRobberHelper(root, ret);
    return Math.max(ret[0], ret[1]);
  }
  private void houseRobberHelper(TreeNode root, int[] max) {
    int[] ret = new int[2];
    if (root == null) return;
    int[] left = new int[2];
    houseRobberHelper(root.left, left);
    int[] right = new int[2];
    houseRobberHelper(root.right, right);
    max[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    max[1] = root.val + left[0] + right[0];
  }

  public TreeNode upsideDown(TreeNode root) {
    if (root == null || root.left == null) return root;
    TreeNode curLeft = root.left, curRight = root.right;
    TreeNode newRoot = upsideDown(root.left);
    curLeft.left = curRight;
    curLeft.right = root;
    root.left = null;
    root.right = null;
    return newRoot;
  }

  public TreeNode upsideDownIter(TreeNode root) {
    TreeNode cur = null, next = null, pre = null, temp = null;
    cur = root;
    while (cur != null) {
      next = cur.left;
      cur.left = temp;
      temp = cur.right;
      cur.right = pre;
      pre = cur;
      cur = next;
    }
    return pre;
  }

  public int longestConsecutiveSequence(TreeNode root) {
    int[] longest = new int[1];
    longest[0] = 0;
    longestHelper(root, longest);
    return longest[0];
  }
  private int longestHelper(TreeNode root, int[] longest) {
    if (root == null) return 0;
    int left = longestHelper(root.left, longest);
    int right = longestHelper(root.right, longest);
    int curLong = 1;
    if (root.left != null && root.val == root.left.val -1) {
      curLong = Math.max(curLong, left + 1);
    }
    if (root.right != null && root.val == root.right.val - 1) {
      curLong = Math.max(curLong, right+1);
    }
    longest[0] = longest[0] < curLong? curLong: longest[0];
    return curLong;
  }

  public int countUnivalSubtrees(TreeNode root) {
    int[] total = new int[1];
    countUnivalHelper(root, total);
    return total[0];
  }
  private boolean countUnivalHelper(TreeNode root, int[] total) {
    if (root == null) return true;
    if (root.left == null && root.right == null) {
      total[0]++;
      return true;
    }
    boolean left = countUnivalHelper(root.left, total);
    boolean right = countUnivalHelper(root.right, total);
    if (left && right) {
      if ((root.left == null || root.val == root.left.val) &&(root.right == null || root.right.val == root.val)) {
        total[0]++;
        return true;
      }
    }
    return false;
  }

  public List<List<Integer>> collectLeaves(TreeNode root) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (root == null) return ret;
    List<List<Integer>> left = collectLeaves(root.left);
    List<List<Integer>> right = collectLeaves(root.right);
    int i = 0;
    int j = 0;
    while (i < left.size() && j < right.size()) {
      List<Integer> temp = new LinkedList<Integer>();
      temp.addAll(left.get(i++));
      temp.addAll(right.get(j++));
      ret.add(temp);

    }
    while (i < left.size()) {
      ret.add(left.get(i++));
    }
    while (j < right.size()) {
      ret.add(right.get(j++));
    }
    List<Integer> temp = new LinkedList<Integer>();
    temp.add(root.val);
    ret.add(temp);
    return ret;
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(5);
    root.left = new TreeNode(1);
    root.right = new TreeNode(5);
    root.left.left = new TreeNode(5);
    root.left.right = new TreeNode(5);
    root.right.right = new TreeNode(5);
    Tree test = new Tree();
    System.out.println(test.countUnivalSubtrees(root));
  }
}

class Codec {

  // Encodes a tree to a single string.
  public String serialize(TreeNode root) {
    if (root == null) return "#";
    if (root.left == null && root.right == null) return Integer.toString(root.val);
    String left = serialize(root.left);
    String right = serialize(root.right);
    return Integer.toString(root.val) + " " + left + " " + right;
  }

  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    String[] elems = data.split(" ");
    int total = elems.length;
    int[] index = new int[2];
    index[0] = 0;
    index[1] = total-1;
    return deserialize(elems, index);
  }

  public TreeNode deserialize(String[] elems, int[] index) {
    if (index[0] > index[1]) return null;
    if (elems[index[0]].equals("#")) {
      index[0]++;
      return null;
    } else {
      TreeNode root = new TreeNode(Integer.parseInt(elems[index[0]]));
      index[0]++;
      root.left = deserialize(elems, index);
      root.right = deserialize(elems, index);
      return root;
    }
  }
}
