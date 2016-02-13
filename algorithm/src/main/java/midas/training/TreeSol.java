package midas.training;

import midas.data.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xli1 on 2/11/16.
 */
public class TreeSol {
  public List<List<TreeNode>> levelTraversal(TreeNode root) {

    List<List<TreeNode>> result = new LinkedList<List<TreeNode>>();
    if (root == null) return result;
    LinkedList<TreeNode> queue1 = new LinkedList<TreeNode>();
    LinkedList<TreeNode> queue2 = new LinkedList<TreeNode>();
    queue1.add(root);
    while(!queue1.isEmpty()) {
      LinkedList<TreeNode> layer = new LinkedList<TreeNode>();
      while (!queue1.isEmpty()){
        TreeNode cur = queue1.poll();
        if (cur.left != null) queue2.add(cur.left);
        if (cur.right != null) queue2.add(cur.right);
        layer.add(cur);
      }
      result.add(layer);
      queue1.clear();
      queue1.addAll(queue2);
      queue2.clear();
    }

    return result;
  }
}
