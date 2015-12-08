package midas.algorithm;

interface Node {
  public Node getLeft(); // These may return null if no left/right child exists
  public Node getRight();
}

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;
}

public class TreeProblem {
  public int countUnivalSubtrees(TreeNode root) {
    if (root == null) {
     return 0;
    }
    return Math.abs(totalUnivalSubtrees(root));
  }

  // return value as number of unival subtrees. if direct child is not unival, use minus sign to indicate it.
  private int totalUnivalSubtrees(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int count = 0;
    int leftcount = totalUnivalSubtrees(root.left);
    int rightcount = totalUnivalSubtrees(root.right);
    if (((root.left == null || root.val == root.left.val) && leftcount>=0) &&
        ((root.right == null || root.val == root.right.val) && rightcount>=0)) {
      count = 1 + Math.abs(leftcount) + Math.abs(rightcount);
    } else {
      count = -(Math.abs(leftcount) + Math.abs(rightcount));
    }
    return count;
  }

  public int maxDepth(Node root) {
    // implementation here
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.getLeft()), maxDepth(root.getRight()));
  }
}
