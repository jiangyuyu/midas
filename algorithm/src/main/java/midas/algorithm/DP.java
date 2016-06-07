package midas.algorithm;

import midas.data.TreeNode;


public class DP {
  public int robMysol(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int N = nums.length;
    int[] benefit = new int[nums.length];
    if (N > 0) benefit[0] = nums[0];
    if (N > 1) benefit[1] = Math.max(nums[0], nums[1]);
    int max = benefit[0];
    if (N > 1) max = max < benefit[1]? benefit[1]:max;
    for (int i = 2; i < nums.length; i++) {
      if (i == 2) {
        benefit[i] = benefit[i-2] + nums[i];
      } else {
        benefit[i] = Math.max(benefit[i-2] + nums[i], benefit[i-3] + nums[i]);
      }
      max = max < benefit[i]? benefit[i] : max;
    }
    return max;
  }
  public int robI(int[] nums, int left, int right) {
    if (nums == null || left > right) return 0;
    if (left == right) return nums[left];
    int pPrev = 0, prev = nums[left], cur = 0;
    for (int i = left + 1; i <= right; i++) {
      cur = Math.max(pPrev + nums[i], prev);
      pPrev = prev;
      prev = cur;
    }
    return cur;
  }

  public int robII(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    if (nums.length == 1) return Math.max(nums[0], nums[1]);
    return Math.max(robI(nums, 0, nums.length - 2), robI(nums, 1, nums.length - 1));
  }

  public int robIII(TreeNode root) {
    int[] val = robRec(root);
    return Math.max(val[0], val[1]);
  }

  public int[] robRec(TreeNode root) {
    int[] ret = new int[2];
    if (root == null) return ret;
    if (root.left == null && root.right == null) {
      ret[0] = root.val;
      ret[1] = 0;
      return ret;
    }
    int[] left = robRec(root.left);
    int[] right = robRec(root.right);
    ret[0] = root.val + left[1] + right[1];
    ret[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    return ret;
  }
}
