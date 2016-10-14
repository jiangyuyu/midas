package midas.learning.algo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


class TrieNode {
  // Initialize your data structure here.
  String val;
  TrieNode[] children;
  public TrieNode() {
    children = new TrieNode[26];
  }
  public TrieNode(String word) {
    val = word;
    children = new TrieNode[26];
  }
}

public class Trie {
  private TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  public TrieNode getRoot() {
    return root;
  }

  // Inserts a word into the trie.
  public void insert(String word) {
    if (word == null || word.length() == 0) return;
    TrieNode cur = root;
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      int index = c - 'a';
      if (cur.children[index] == null) {
        cur.children[index] = new TrieNode();
      }
      cur = cur.children[index];
    }
    cur.val = word;
  }

  // Returns if the word is in the trie.
  public boolean search(String word) {
    if (word == null || word.length() == 0) return false;
    TrieNode cur = root;
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      int index = c - 'a';
      if (cur.children[index] == null) return false;
      cur = cur.children[index];
    }
    if (cur.val.equals(word)) return true;
    return false;
  }

  // Returns if there is any word in the trie
  // that starts with the given prefix.
  public boolean startsWith(String prefix) {
    if (prefix == null || prefix.length() == 0) return false;
    TrieNode cur = root;
    for (int i = 0; i < prefix.length(); i++) {
      char c = prefix.charAt(i);
      if (cur.children[c-'a'] == null) return false;
      cur = cur.children[c - 'a'];
    }
    for (TrieNode node : cur.children) {
      if (node != null || node.val.equals(prefix)) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    Trie trie = new Trie();
    trie.insert("app");
    trie.insert("apple");
    System.out.println(trie.search("app"));
  }
}

class TrieSolution {
  // search all available dictionary words in a board
  public List<String> findWords(char[][] board, String[] words) {
    List<String> ret = new LinkedList<String>();
    if (board == null || words == null || words.length == 0) return ret;
    Trie trie = new Trie();
    for (String w : words) trie.insert(w);

    boolean[][] visited = new boolean[board.length][board[0].length];
    for (boolean[] a : visited) Arrays.fill(a, false);

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        search(board, trie.getRoot(), i, j, visited, ret);
      }
    }
    return ret;
  }
  private void search(char[][] board, TrieNode cur, int i, int j, boolean[][] visited, List<String> ret) {
    if (cur != null && !cur.val.isEmpty()) {
      ret.add(cur.val);
      return;
    }
    if (i < 0 || i > board.length || j < 0 || j > board[0].length || visited[i][j]) return;
    char c = board[i][j];
    if (cur.children[c - 'a'] == null) return;
    visited[i][j] = true;
    search(board, cur.children[c-'a'], i+1, j, visited, ret);
    search(board, cur.children[c-'a'], i-1, j, visited, ret);
    search(board, cur.children[c-'a'], i, j+1, visited, ret);
    search(board, cur.children[c-'a'], i, j-1, visited, ret);
    visited[i][j] = false;
  }

  public static void main(String[] args) {
    String[] arr = {"oaan","etae","ihkr","iflv"};
    char[][] board = new char[4][4];
    for (int i = 0; i < arr.length; i++) {
      board[i] = arr[i].toCharArray();
    }
    String[] words = {"oath","pea","eat","rain"};
    TrieSolution test = new TrieSolution();
    System.out.println(test.findWords(board, words));
  }
}