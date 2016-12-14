package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// master change

public class BackTrack {
  public List<String> letterCombinations(String digits) {
    if (digits == null || digits.length() == 0) {
      return new ArrayList<String>();
    }
    HashMap<Character, String> letterMap = new HashMap<Character, String>();
    letterMap.put('2', "a b c");
    letterMap.put('3', "d e f");
    letterMap.put('4', "g h i");
    letterMap.put('5', "j k l");
    letterMap.put('6', "m n o");
    letterMap.put('7', "p q r s");
    letterMap.put('8', "t u v");
    letterMap.put('9', "w x y z");

    return letterCombinations(digits, 0, letterMap);
  }
  private List<String> letterCombinations(String digits, int start, HashMap<Character, String> letterMap) {
    List<String> ret = new ArrayList<String>();
    char cur = digits.charAt(start);
    String[] letters = letterMap.get(cur).split(" ");
    if (start == digits.length()-1) {
      if (letterMap.containsKey(cur)) {
        ret.addAll(Arrays.asList(letters));
      }
      return ret;
    }
    List<String> part = letterCombinations(digits, start + 1, letterMap);
    for (String letter: letters) {
      for (String s : part) {
        ret.add(letter + s);
      }
    }
    return ret;
  }

  public boolean wordSearch(char[][] board, String word) {
    if (board == null || board.length == 0 || board[0].length == 0 || word == null || word.length() ==0 )
      return false;
    boolean[][] visited = new boolean[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        if (wordSearch(board, i, j, word, 0, visited)) return true;
      }
    }
    return false;
  }

  private boolean wordSearch(char[][] board, int i, int j, String word, int cur, boolean[][] visited) {
    if (cur == word.length()) return true;
    if (i >= board.length || i < 0 || j >= board[0].length || j < 0) return false;
    if (!visited[i][j] && board[i][j] == word.charAt(cur)) {
      visited[i][j] = true;
      if (wordSearch(board, i+1, j, word, cur+1, visited) ||
          wordSearch(board, i, j+1, word, cur+1, visited) ||
          wordSearch(board, i-1, j, word, cur+1, visited) ||
          wordSearch(board, i, j-1, word, cur+1, visited)) {
        return true;
      }
      visited[i][j] = false;
    }
    return false;
  }

  public List<String> wordSetSearchNaive(char[][] board, String[] words) {
    List<String> ret = new ArrayList<String>();
    if (board == null || board.length == 0 || board[0].length == 0) return ret;
    if (words == null || words.length == 0) return ret;
    Set<String> s = new HashSet<String>();
    for (int i = 0; i < words.length; i++) {
      if (wordSearch(board, words[i])) s.add(words[i]);
    }
    for (String str : s) ret.add(str);
    return ret;
  }

  public List<List<Integer>> permute(int[] nums) {
    if (nums == null || nums.length == 0) return new LinkedList<List<Integer>>();
    return permute(nums, 0);
  }
  private List<List<Integer>> permute(int[] nums, int cur) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (cur == nums.length-1) {
      List<Integer> l = new LinkedList<Integer>();
      l.add(nums[cur]);
      ret.add(l);
      return ret;
    }
    List<List<Integer>> partial = permute(nums, cur+1);
    for (List<Integer> l : partial) {
      for (int i = 0; i < l.size(); i++) {
        List<Integer> copy = new LinkedList<Integer>(l);
        copy.add(i, nums[cur]);
        ret.add(copy);
      }
      List<Integer> copy = new LinkedList<Integer>(l);
      copy.add(nums[cur]);
      ret.add(copy);
    }
    return ret;
  }

  public List<List<Integer>> permuteLight(int[] nums) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    permuteHelper(nums, 0, ret);
    return ret;
  }
  private void permuteHelper(int[] nums, int i, List<List<Integer>> ret) {
    if (i == nums.length) {
      List<Integer> l = new LinkedList<Integer>();
      for (int a : nums) l.add(a);
      ret.add(l);
      return;
    }
    for (int j = i; j < nums.length; j++) {
      swap(nums, i, j);
      permuteHelper(nums, i+1, ret);
      swap(nums, i, j);
    }
  }
  private void swap(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (nums == null || nums.length == 0) return ret;
    permuteUnique(nums, 0, ret);
    return ret;
  }
  private void permuteUnique(int[] nums, int cur, List<List<Integer>> ret) {
    if (cur == nums.length) {
      List<Integer> l = new LinkedList<Integer>();
      for (int a : nums) l.add(a);
      ret.add(l);
      return;
    }
    for (int i = cur; i < nums.length; i++) {
      if (i != cur && alreadySeenCurrentNum(nums, cur, i)) continue;
      swap(nums, cur, i);
      permuteUnique(nums, cur+1, ret);
      swap(nums, cur, i);
    }
  }
  private boolean alreadySeenCurrentNum(int[] num, int i, int j) {
    for (int k = i; k < j; k++) {
      if (num[k] == num[j]) return true;
    }
    return false;
  }

  // use set to remove duplicate permutation.
  public List<List<Integer>> permuteUnique2(int[] nums) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    ret.add(new LinkedList<Integer>());
    for (int i = 0; i < nums.length; i++) {
      Set<List<Integer>> curSet = new HashSet<List<Integer>>();
      for (List<Integer> l : ret) {
        for (int j = 0; j <= l.size(); j++) {
          List<Integer> temp = new LinkedList<Integer>(l);
          temp.add(j, nums[i]);
          curSet.add(temp);
        }
      }
      ret.clear();
      ret.addAll(curSet);
    }
    return ret;
  }

  public String getPermutation(int n, int k) {
    if (n == 0 || k == 0) return null;
    ArrayList<Integer> pool = new ArrayList<Integer>();
    int total = 1;
    for (int i = 1; i <= n; i++) {
      pool.add(i);
      total = total * i;
    }
    total = total/n; //bucket size
    int count = n-1;
    k--;
    StringBuffer sb = new StringBuffer();
    while (count > 0) {
      sb.append(pool.get(k/total));
      pool.remove(k/total);
      k = k%total;
      total = total/count;
      count--;
    }
    sb.append(pool.get(0));
    return sb.toString();
  }

  public List<String> generateParenthesis(int n) {
    List<String> ret = new LinkedList<String>();
    if (n == 0) return ret;
    char[] arr = new char[2 * n];
    generateParenthesis(n, n, arr, 0, ret);
    return ret;
  }
  private void generateParenthesis(int left, int right, char[] arr, int k, List<String> ret) {
    if (left == 0 && right == 0) {
      ret.add(new String(arr));
      return;
    }
    if (left < 0 || right < 0 || left > right) return;
    arr[k] = '(';
    generateParenthesis(left-1, right, arr, k+1, ret);
    arr[k] = ')';
    generateParenthesis(left, right-1, arr, k+1, ret);
  }

  public List<String> findAllWordBreak(String s, Set<String> dict) {
    if (s == null || s.length() == 0) return new LinkedList<String>();
    boolean[] possible = new boolean[s.length() + 1];
    Arrays.fill(possible, true);
    return findAllWordBreak(s, 0, possible, dict);
  }

  private List<String> findAllWordBreak(String s, int cur, boolean[] possible, Set<String> dict) {
    List<String> ret = new LinkedList<String>();
    if (cur == s.length()) {
      ret.add("");
      return ret;
    }
    for (int i = cur; i < s.length(); i++) {
      String prefix = s.substring(cur, i+1);
      if (dict.contains(prefix) && possible[i+1]) {
        List<String> part = findAllWordBreak(s, i+1, possible, dict);
        if (part.isEmpty()) possible[i+1] = false;
        else {
          for (String str : part) {
            ret.add((prefix + " " + str).trim());
          }
        }
      }
    }
    return ret;
  }

  public List<List<Integer>> combinationSum(int[] nums, int target) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (nums == null || nums.length == 0) return ret;
    return combinationSum(nums, 0, target);
  }
  private List<List<Integer>> combinationSum(int[] nums, int cur, int target) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (target < 0 || cur >= nums.length) {
      return ret;
    }
    if (target == 0) {
      List<Integer> l = new LinkedList<Integer>();
      ret.add(l);
      return ret;
    }

    ret.addAll(combinationSum(nums, cur+1, target));
    List<List<Integer>> part2 = combinationSum(nums, cur, target - nums[cur]);
    for (List<Integer> l : part2) {
      l.add(nums[cur]);
      ret.add(l);
    }
    return ret;
  }

  public List<List<Integer>> combinationSum2(int[] nums, int target) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (nums == null || nums.length == 0) return ret;
    Arrays.sort(nums);
    return combinationSum2(nums, 0, target);
  }
  private List<List<Integer>> combinationSum2(int[] nums, int cur, int target) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (target == 0) {
      List<Integer> l = new LinkedList<Integer>();
      ret.add(l);
      return ret;
    }
    if (target < 0 || cur >= nums.length) {
      return ret;
    }
    List<List<Integer>> part1 = combinationSum2(nums, cur+1, target-nums[cur]);
    int i = 0;
    for (i = cur+1; i < nums.length; i++) {
      if (nums[i] != nums[i-1]) break;
    }
    ret.addAll(combinationSum2(nums, i, target));
    for (List<Integer> l : part1) {
      l.add(nums[cur]);
      ret.add(l);
    }
    return ret;
  }

  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (nums == null || nums.length == 0) return ret;
    return subsets(nums, 0);
  }
  private List<List<Integer>> subsets(int[] nums, int cur) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (cur == nums.length) {
      ret.add(new LinkedList<Integer>());
      return ret;
    }
    List<List<Integer>> part = subsets(nums, cur+1);
    for (List<Integer> l : part) {
      ret.add(l);
      List<Integer> l2 = new LinkedList<Integer>();
      l2.add(nums[cur]);
      l2.addAll(l);
      ret.add(l2);
    }
    return ret;
  }

  public List<List<Integer>> subsetsWithDup(int[] nums) {
    if (nums == null || nums.length == 0) return new LinkedList<List<Integer>>();
    Arrays.sort(nums);
    return subsetsWithDup(nums, 0);
  }
  private List<List<Integer>> subsetsWithDup(int[] nums, int cur) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (cur == nums.length) {
      List<Integer> l = new LinkedList<Integer>();
      ret.add(l);
      return ret;
    }
    int i = cur+1;
    for (; i < nums.length; i++) {
      if (nums[i] != nums[i-1]) break;
    }
    int count = i - cur;
    List<List<Integer>> part = subsetsWithDup(nums, i);
    for (List<Integer> l : part) {
      ret.add(l);
      for (int k = 0; k < count; k++) {
        List<Integer> temp = new LinkedList<Integer>();
        temp.addAll(l);
        for (int t = 0; t <= k; t++) temp.add(0, nums[cur]);
        ret.add(temp);
      }
    }
    return ret;
  }

  public List<Integer> graycode(int n) {
    List<Integer> ret = new LinkedList<Integer>();
    if (n == 0) {
      ret.add(0);
      return ret;
    }
    if (n == 1) {
      ret.add(0);
      ret.add(1);
      return ret;
    }
    List<Integer> part = graycode(n-1);
    for (Integer x : part) {
      ret.add(x);
    }
    for (int i = part.size()-1; i >= 0; i--) {
      ret.add((1 << (n-1)) + part.get(i));
    }
    return ret;
  }

  public List<List<Integer>> combine(int n, int k) {
    if (n < k) return null;
    return combine(1, n, k);
  }
  public List<List<Integer>> combine(int i, int n, int k) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (k == 0) {
      List<Integer> l = new LinkedList<Integer>();
      ret.add(l);
      return ret;
    }
    if (i > n || (n-i+1) < k) return ret;
    List<List<Integer>> part1 = combine(i+1, n, k);
    ret.addAll(part1);
    List<List<Integer>> part2 = combine(i+1, n, k-1);
    for (List<Integer> l : part2) {
      l.add(0, i);
      ret.add(l);
    }
    return ret;
  }

  public List<String> restoreIP(String s) {
    if (s == null || s.length() == 0) return new LinkedList<String>();
    return restoreIP(s, 0, 4);
  }
  private List<String> restoreIP(String s, int i, int count) {
    List<String> ret = new LinkedList<String>();
    if (i == s.length() && count == 0) {
      ret.add("");
      return ret;
    }
    if (i > s.length() || count < 0) return ret;

    for (int j = i; j < s.length(); j++) {
      String cur = s.substring(i, j+1);
      if (cur.length() > 3) continue;
      int ip = Integer.parseInt(cur);
      if (cur.length() != Integer.toString(ip).length()) continue; // avoid like "00", or "010"
      if (ip >= 0 && ip <= 255) {
        List<String> part = restoreIP(s, j+1, count-1);
        for (String partialIP : part) {
          if (count > 1)
            ret.add(cur + "." + partialIP);
          else
            ret.add(cur);
        }
      }
    }
    return ret;
  }

  public List<List<String>> solveNQueens(int n) {
    List<List<String>> ret = new LinkedList<List<String>>();
    if (n <= 0) return ret;
    int[] pos = new int[n];
    solveNQueens(n, pos, 0, ret);
    return ret;
  }
  private void solveNQueens(int n, int[] pos, int cur, List<List<String>> ret) {
    if (cur == n) {
      List<String> sol = new LinkedList<String>();
      for (int i = 0; i < n; i++) {
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < n; j++) {
          if (j == pos[i]) sb.append("Q");
          else sb.append(".");
        }
        sol.add(sb.toString());
      }
      ret.add(sol);
      return;
    }
    for (int i = 0; i < n; i++) {
      pos[cur] = i;
      if (isValid(pos, cur)) solveNQueens(n, pos, cur+1, ret);
    }
  }
  public int solveNQueens2(int n) {
    if (n <= 0) return 0;
    int[] pos = new int[n];
    int[] total = new int[1];
    solveNQueens2(n, pos, 0, total);
    return total[0];
  }
  private void solveNQueens2(int n, int[] pos, int cur, int[] total) {
    if (cur == n) {
      total[0]++;
      return;
    }
    for (int i = 0; i < n; i++) {
      pos[cur] = i;
      solveNQueens2(n, pos, cur+1, total);
    }
  }

  private boolean isValid(int[] pos, int cur) {
    for (int i = 0; i < cur; i++) {
      if (pos[cur] == pos[i] || cur-i == pos[cur]-pos[i] || i - cur == pos[cur]-pos[i]) return false;
    }
    return true;
  }

  public void solveSudoku(char[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) return;
    int n = board.length;
    solveSudoku(board, 0, n);
  }
  private boolean solveSudoku(char[][] board, int cur, int n) {
    if (cur == n*n) return true;
    int row = cur/n, col = cur %n;
    if (board[row][col] != '.') return solveSudoku(board, cur + 1, n);
    for (int k = 1; k <= n; k++) {
      board[row][col] = (char)('0'+k);
      if (isValidSudoku(board, row, col)) {
        boolean next = solveSudoku(board, cur+1, n);
        if (next) return true;
      }
    }
    board[row][col] = '.'; // all trial fail, we need to go back to last step. should make it unfilled.
    return false;
  }
  private boolean isValidSudoku(char[][] board, int r, int c) {
    int n = board.length;
    // conflict in the same row/col
    for (int i = 0; i < n; i++) {
      if (i != c && board[r][i] == board[r][c]) return false;
      if (i != r && board[i][c] == board[r][c]) return false;
    }
    int startr = r/3 * 3, startc = c/3*3;
    for (int k = startr; k < startr+3; k++) {
      for (int t = startc; t < startc+3; t++) {
        if ((k != r || t != c) && board[k][t] == board[r][c]) return false;
      }
    }
    return true;
  }

  public List<List<String>> partitionPalindrome(String s) {
    List<List<String>> ret = new LinkedList<List<String>>();
    if (s == null || s.length() == 0) return ret;
    return partitionPalindrome(s, 0);
  }
  private List<List<String>> partitionPalindrome(String s, int cur) {
    List<List<String>> ret = new LinkedList<List<String>>();
    if (cur == s.length()) {
      List<String> l = new LinkedList<String>();
      ret.add(l);
      return ret;
    }
    for (int i = cur; i < s.length(); i++) {
      String part = s.substring(cur, i+1);
      if (isPalindrome(part)) {
        List<List<String>> partial = partitionPalindrome(s, i+1);
        for (List<String> l : partial) l.add(0, part);
        ret.addAll(partial);
      }
    }
    return ret;
  }
  private boolean isPalindrome(String s) {
    int i = 0, j = s.length()-1;
    while (i < j) {
      if (s.charAt(i) != s.charAt(j)) return false;
      i++;
      j--;
    }
    return true;
  }

  public static void main(String[] args) {
    BackTrack test = new BackTrack();
    int[] nums = {2, 2,  1, 1};
    Set<String> dict = new HashSet<String>();
    String[] strs = {"hot","dot","dog","lot","log"};
    dict.addAll(Arrays.asList(strs));
    char[][] board = {"oaan".toCharArray(),"etae".toCharArray(),"ihkr".toCharArray(),"iflv".toCharArray()};
    System.out.println(test.wordSearch(board, "oath"));
  }
}
