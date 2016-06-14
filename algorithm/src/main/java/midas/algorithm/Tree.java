package midas.algorithm;

import java.util.Stack;
import midas.data.TreeNode;

public class Tree {
  public int largestBSTSubtree(TreeNode root) {
    int[] minMaxTotal = new int[2];
    minMaxTotal[0] = Integer.MIN_VALUE;
    minMaxTotal[1] = Integer.MAX_VALUE;
    int max = largestBSTUtil(root, minMaxTotal);
    return Math.abs(max);
  }
  private boolean largestBSTSubstreeHelper(TreeNode root, int[] minMaxTotal) {
    if (root == null) return true;
    int[] leftMinMaxTotal = new int[3];
    leftMinMaxTotal[0] = Integer.MIN_VALUE;
    leftMinMaxTotal[1] = Integer.MAX_VALUE;
    leftMinMaxTotal[2] = 0;
    int[] rightMinMaxTotal = new int[3];
    rightMinMaxTotal[0] = Integer.MIN_VALUE;
    rightMinMaxTotal[1] = Integer.MAX_VALUE;
    rightMinMaxTotal[2] = 0;
    boolean left = largestBSTSubstreeHelper(root.left, leftMinMaxTotal);
    boolean right = largestBSTSubstreeHelper(root.right, rightMinMaxTotal);
    if (left && right) {
      if ((root.left == null || leftMinMaxTotal[1] <= root.val) && (root.right == null || rightMinMaxTotal[0] > root.val)) {
        minMaxTotal[2] = leftMinMaxTotal[2] + rightMinMaxTotal[2] + 1;
        minMaxTotal[0] = root.left == null? root.val : leftMinMaxTotal[0];
        minMaxTotal[1] = root.right == null ? root.val : rightMinMaxTotal[1];
        return true;
      }
    }
    minMaxTotal[2] = Math.max(leftMinMaxTotal[2], rightMinMaxTotal[2]);
    return false;
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

  public boolean isValidBST(TreeNode root) {
    if (root == null) return true;
    long min = Long.MIN_VALUE, max = Long.MAX_VALUE;
    return isValidBST(root, min, max);
  }
  private boolean isValidBST(TreeNode root, long min, long max) {
    if (root == null) return true;
    if (root.val <= min || root.val >= max) return false;
    return isValidBST(root.left, min, root.val) && isValidBST(root.right, root.val, max);
  }

  public boolean isValidBSTIterative(TreeNode root) {
    return false;
  }

  public boolean isValidSerialization(String preorder) {
    if (preorder == null || preorder.isEmpty()) return false;
    String[] values = preorder.split(",");
    return isValidSerialization(values, 0, preorder.length()-1);
  }
  // naive recursion
  private boolean isValidSerialization(String[] preorder, int start, int end) {
    if (preorder[start].equals("#")) {
      if (start == end) return true;
      else return false;
    }
    for (int i = start + 2; i < end; i++) {
      if (isValidSerialization(preorder, start+1, i) && isValidSerialization(preorder, i+1, end)) {
        return true;
      }
    }
    return false;
  }

  public boolean isValidSerializationStack(String preorder) {
    if (preorder == null || preorder.isEmpty()) return false;
    String[] values = preorder.split(",");
    Stack<String> stack = new Stack<String>();
    for (int i = 0; i < values.length; i++) {
      stack.push(values[i]);
      while (stack.size() > 2
          && stack.peek().equals("#")
          && stack.get(stack.size()-2).equals("#")
          && !stack.get(stack.size()-3).equals("#")) {
        for (int j = 0; j < 3; j++) {
          stack.pop();
        }
        stack.push("#");
      }
    }
    return stack.size() == 1 && stack.peek().equals("#");
  }

  public boolean isValidSerializationDegree(String preorder) {
    if (preorder == null || preorder.isEmpty()) return false;
    String[] values = preorder.split(",");
    int diff = 1;
    for (String val : values) {
      if (--diff < 0) return false;
      if (!val.equals("#")) diff += 2;
    }
    return diff == 0;
  }

}
