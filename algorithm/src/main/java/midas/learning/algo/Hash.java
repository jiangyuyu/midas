package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import midas.data.Point;
import midas.data.TreeNode;


public class Hash {
  public int[] twoSum(int[] arr, int target) {
    if (arr == null || arr.length < 2) return null;
    int[] ret = new int[2];
    int n = arr.length;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i = 0; i < n; i++) {
      if (map.containsKey(target - arr[i])) {
        ret[0] = map.get(target-arr[i]);
        ret[1] = i;
        return ret;
      } else {
        map.put(arr[i], i);
      }
    }
    return ret;
  }

  public int singleNumber(int[] nums) {
    if (nums == null || nums.length == 0) throw new IllegalArgumentException();
    int n = nums.length;
    HashSet<Integer> set = new HashSet<Integer>();
    for (int i = 0; i < n; i++) {
      if (!set.contains(nums[i])) set.add(nums[i]);
      else {
        set.remove(nums[i]);
      }
    }
    int ret = 0;
    for (Integer i : set) ret = i;
    return ret;
  }

  public boolean isHappyNumber(int num) {
    if (num < 0) return false;
    HashSet<Integer> set = new HashSet<Integer>();
    while (num != 1 && !set.contains(num)) {
      set.add(num);
      //find digit sum
      int sum = 0;
      while (num > 0) {
        sum += (num % 10) * (num%10);
        num = num/10;
      }
      num = sum;
    }
    if (num == 1) return true;
    return false;
  }

  public boolean containDuplicates(int[] nums) {
    if (nums == null || nums.length == 0) return false;
    int n = nums.length;
    HashSet<Integer> set = new HashSet<Integer>();
    for (int i = 0; i < n; i++) {
      if (!set.contains(nums[i])) {
        set.add(nums[i]);
      } else {
        return true;
      }
    }
    return false;
  }

  public int maxPointsInLine(Point[] points) {
    if (points == null || points.length == 0) return 0;
    int n = points.length;
    if (n <= 2) return n;
    HashMap<Double, Integer> groups = new HashMap<Double, Integer>();
    int max = 0;
    for (int i = 0; i < n; i++) {
      groups.clear();
      int same = 1, vertical = 0, horizontal = 0;
      for (int j = i+1; j < n; j++) {
        if (points[i].x == points[j].x && points[i].y == points[j].y) same++;
        else if (points[i].x == points[j].x) vertical++;
        else if (points[i].y == points[j].y) horizontal++;
        else {
          double slope = (double)(points[i].y - points[j].y)/(double)(points[i].x - points[j].x);
          if (groups.containsKey(slope)) {
            groups.put(slope, groups.get(slope) + 1);
          } else {
            groups.put(slope, 1);
          }
        }
      }
      for (Integer v : groups.values()) {
        max = Math.max(max, v + same);
      }
      max = Math.max(max, same + vertical);
      max = Math.max(max, same + horizontal);
    }
    return max;
  }

  public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> ret = new LinkedList<List<String>>();
    if (strs == null || strs.length == 0) return ret;
    HashMap<String, LinkedList<String>> map = new HashMap<String, LinkedList<String>>();
    for (String str : strs) {
      char[] temp = str.toCharArray();
      Arrays.sort(temp);
      String key = new String(temp);
      if (!map.containsKey(key)) map.put(key, new LinkedList<String>());
      map.get(key).add(str);
    }
    for (String key : map.keySet()) {
      ret.add(map.get(key));
    }
    return ret;
  }

  public boolean validAnagram(String s, String t) {
    if (s == null || t == null) return false;
    int[] count = new int[256];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i)]++;
    }
    for (int j = 0; j < t.length(); j++) {
      count[t.charAt(j)]--;
    }
    for (int i = 0; i < 256; i++) {
      if (count[i] != 0) return false;
    }
    return true;
  }

  public boolean containsNearbyDuplicates(int[] arr, int k) {
    if (arr == null || k <= 0) return false;
    HashMap<Integer, Integer> pos = new HashMap<Integer, Integer>();
    for (int i = 0; i < arr.length; i++) {
      if (!pos.containsKey(arr[i])) {
        pos.put(arr[i], i);
      } else {
        int prev = pos.get(arr[i]);
        if (i - prev <= k) return true;
        pos.put(arr[i], i);
      }
    }
    return false;
  }

  public boolean isValidSudoku(char[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) return false;
    int m = board.length, n = board[0].length;
    for (int i = 0; i < m; i++) {
      int[] count = new int[9];
      for (int j = 0; j < n; j++) {
        if (board[i][j] == '.') continue;
        if (count[board[i][j] - '0' - 1] == 0) {
          count[board[i][j] - '0' - 1] = 1;
        } else {
          return false;
        }
      }
    }
    for (int j = 0; j < n; j++) {
      int[] count = new int[9];
      for (int i = 0; i < m; i++) {
        if (board[i][j] == '.') continue;
        if (count[board[i][j] - '0' - 1] == 0) {
          count[board[i][j] - '0' - 1] = 1;
        } else {
          return false;
        }
      }
    }
    for (int i = 0; i < m; i = i+3) {
      for (int j = 0; j < n; j= j+3) {
        int[] count = new int[9];
        for (int p = i; p < i+3; p++) {
          for (int q = j; q < j+3; q++) {
            if (board[p][q] == '.') continue;
            if (count[board[p][q] - '0' -1] == 0) {
              count[board[p][q] - '0' - 1]++;
            } else {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  public int numberOfBoomerangs(int[][] points) {
    if (points == null || points.length < 3) return 0;
    int n = points.length;
    int sum = 0;
    for (int i = 0; i < n; i++) {
      HashMap<Double, Integer> map = new HashMap<Double, Integer>();
      for (int j = 0; j < n; j++) {
        if (i == j) continue;
        double distance = Math.sqrt((points[i][0] - points[j][0]) * (points[i][0] - points[j][0]) + (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]));
        if (!map.containsKey(distance)) map.put(distance, 0);
        map.put(distance, map.get(distance) + 1);
      }
      for (Integer v : map.values()) {
        if (v >= 2) sum += v * (v-1);
      }
    }
    return sum;
  }

  public List<Integer> findAnagrams(String s, String p) {
    List<Integer> ret = new LinkedList<Integer>();
    if (s == null || p == null || s.isEmpty() || p.isEmpty()) return ret;
    int[] pindex = new int[256];
    int m = s.length(), n = p.length();
    for (int i = 0; i < n; i++) {
      pindex[p.charAt(i)]++;
    }
    int[] sindex = new int[256];
    for (int i = 0; i <= m-n; i++) {
      Arrays.fill(sindex, 0);
      int j = i;
      for (j = i; j < i+n; j++) {
        sindex[s.charAt(j)]++;
        if (sindex[s.charAt(j)] > pindex[s.charAt(j)]) break;
      }
      if (j == i+n) ret.add(i);
    }
    return ret;
  }

  public boolean lineReflection(int[][] points) {
    if (points.length == 0) return true;
    int m = points.length;
    HashMap<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>();
    int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
    for (int[] p : points) {
      max = Math.max(max, p[0]);
      min = Math.min(min, p[0]);
      if (!map.containsKey(p[1])) map.put(p[1], new HashSet<Integer>());
      map.get(p[1]).add(p[0]);
    }
    double middle = (min + max)/2.0;
    for (int[] p : points) {
      if (!map.get(p[1]).contains((int)(middle * 2 - p[0]))) return false;
    }
    return true;
  }

  public int longestPalindrome(String s) {
    if (s == null || s.isEmpty()) return 0;
    int[] count = new int[256];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i)]++;
    }
    boolean singleChar = false;
    int max = 0;
    for (int i = 0; i < 256; i++) {
      if (count[i] > 0) {
        max += count[i]/2 * 2;
      }
    }
    if (max < s.length()) return max+1;
    return max;
  }

  public String rearrangeString(String str, int k) {
    if (str == null) return "";
    if (k == 0) return str;
    HashMap<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < str.length(); i++) {
      if (!map.containsKey(str.charAt(i))) map.put(str.charAt(i), 0);
      map.put(str.charAt(i), map.get(str.charAt(i))+1);
    }
    PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<Map.Entry<Character, Integer>>(
        new Comparator<Map.Entry<Character, Integer>>() {
          public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
            if (a.getValue() < b.getValue()) return 1;
            else if (a.getValue() == b.getValue()) return a.getKey().compareTo(b.getKey());
            else return -1;
          }
        }
    );
    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
      pq.add(entry);
    }
    StringBuffer ret = new StringBuffer();
    int len = str.length();
    while (!pq.isEmpty()) {
      int cnt = Math.min(len, k);
      List<Map.Entry<Character, Integer>> temp = new LinkedList<Map.Entry<Character, Integer>>();
      for (int i = 0; i < cnt; i++) {
        if (pq.isEmpty()) return "";
        Map.Entry<Character, Integer> e = pq.poll();
        ret.append(e.getKey());
        len--;
        if (e.getValue() - 1 > 0) {
          e.setValue(e.getValue() - 1);
          temp.add(e);
        }
      }
      for (Map.Entry<Character, Integer> entry : temp) pq.add(entry);
    }
    return ret.toString();
  }

  public boolean isStrobogrammatic(String num) {
    if (num == null || num.isEmpty()) return false;
    char[] array = num.toCharArray();
    int left = 0, right = array.length - 1;
    while (left <= right) {
      if (left < right) {
        char l = array[left], r = array[right];
        if (l == r) {
          if (l != '0' && l != '1' && l != '8') return false;
        } else {
          if (!((l == '9' && r == '6') || (l == '6' && r == '9'))) return false;
        }
      } else {
        char l = array[left];
        if (l != '0' && l != '1' && l != '8') return false;
      }
      left++;
      right--;
    }
    return true;
  }

  public List<List<String>> groupStrings(String[] strings) {
    List<List<String>> ret = new LinkedList<List<String>>();
    if (strings == null || strings.length == 0) return ret;
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    for (String s : strings) {
      char[] arr = s.toCharArray();
      if (arr.length == 0) {
        if (!map.containsKey("")) map.put("", new LinkedList<String>());
        map.get("").add(s);
      } else {
        char start = arr[0];
        int diff = arr[0] - 'a';
        for (int i = 0; i < arr.length; i++) {
          if (arr[i] - 'a' >= diff)
            arr[i] = (char)(arr[i] - diff);
          else {
            arr[i] = (char)(arr[i] + 26 - diff);
          }
        }
        String key = new String(arr);
        if (!map.containsKey(key)) map.put(key, new LinkedList<String>());
        map.get(key).add(s);
      }
    }
    for (List<String> v : map.values()) {
      ret.add(v);
    }
    return ret;
  }

  public List<List<Integer>> verticalOrderTree(TreeNode root) {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    if (root == null) return ret;
    HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
    HashMap<TreeNode, Integer> trace = new HashMap<TreeNode, Integer>();
    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
    queue.add(root);
    trace.put(root, 0);
    while (!queue.isEmpty()) {
      TreeNode cur = queue.poll();
      int level = trace.get(cur);
      if (!map.containsKey(level)) map.put(level, new LinkedList<Integer>());
      map.get(level).add(cur.val);
      if (cur.left != null) {
        queue.add(cur.left);
        trace.put(cur.left, level-1);
      }
      if (cur.right != null) {
        queue.add(cur.right);
        trace.put(cur.right, level+1);
      }
    }
    List<Integer> l = new LinkedList<Integer>();
    for (Integer k : map.keySet()) l.add(k);
    Collections.sort(l);
    for (Integer k : l) ret.add(map.get(k));
    return ret;
  }

  public int lengthOfLongestSubstringKDistinct(String s, int k) {
    if (s == null || s.isEmpty() || k == 0) return 0;
    int left = 0, right = 0;
    int max = 0;
    int[] count = new int[256];
    int total = 0;
    for (right = 0; right < s.length(); right++) {
      char cur = s.charAt(right);
      if (count[cur] == 0) {
        total++;
      }
      count[cur]++;
      if (total == k) {
        max = Math.max(max, right-left+1);
      } else if (total > k) {
        while (left < right && total > k) {
          char c = s.charAt(left);
          count[c]--;
          if (count[c] == 0) total--;
          left++;
        }
      }
    }
    max = Math.max(max, right - left);
    return max;
  }

  public int shortestWordDistanceOneCall(String[] words, String w1, String w2) {
    int shortest = words.length;
    int pos1 = -1, pos2 = -1;
    for (int i = 0; i < words.length; i++) {
      if (words[i].equals(w1)) {
        pos1 = i;
      } else if (words[i].equals(w2)) {
        pos2 = i;
      }
      if (pos1 != -1 && pos2 != -1) shortest = Math.min(shortest, Math.abs(pos1-pos2));
    }
    return shortest;
  }

  public boolean palindromePermutation(String s) {
    HashMap<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!map.containsKey(c)) map.put(c, 0);
      map.put(c, map.get(c) + 1);
    }
    int oddcount = 0;
    for (Character k : map.keySet()) {
      int count = map.get(k);
      if (count % 2 == 1) oddcount++;
      if (oddcount > 1) return false;
    }
    return true;
  }

  public int maxLengthSubarraySum(int[] arr, int k) {
    int maxlen = 0;
    if (arr == null || arr.length == 0) return 0;
    int sum = 0;
    HashMap<Integer, Integer> sumMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < arr.length; i++) {
      sum += arr[i];
      if (!sumMap.containsKey(sum)) sumMap.put(sum, i);
      if (sum == k) maxlen = Math.max(maxlen, i+1);
      else if (sumMap.containsKey(sum-k)) {
        maxlen = Math.max(maxlen, i - sumMap.get(sum-k));
      }
    }
    return maxlen;
  }

  public int[][] multiply(int[][] A, int[][] B) {
    if (A == null || B == null || A.length == 0 || B.length == 0) return null;
    int rowa = A.length, cola = A[0].length;
    int rowb = B.length, colb = B[0].length;
    int[][] ret = new int[rowa][colb];
    for (int i = 0; i < rowa; i++) {
      for (int j = 0; j < cola; j++) {
        if (A[i][j] != 0) {
          for (int k = 0; k < colb; k++) {
            if (B[j][k] != 0) {
              ret[i][k] += A[i][j] * B[j][k];
            }
          }
        }
      }
    }
    return ret;
  }
  public char findDifferenceChar(String s, String t) {
    int[] count = new int[256];
    for (int i = 0; i < t.length(); i++) {
      count[t.charAt(i)]++;
    }
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i)]--;
    }
    for (int i = 0; i < 256; i++) {
      if (count[i] > 0) return (char)(i);
    }
    return (char)(0);
  }

  public String sortByCharFrequency(String s) {
    if (s == null || s.isEmpty()) return s;
    HashMap<Character, Integer> map = new HashMap<Character, Integer>();

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!map.containsKey(c)) {
        map.put(c, 0);
      }
      map.put(c, map.get(s.charAt(i))+1);
    }
    List<Character> l = new ArrayList<Character>(map.keySet());
    Collections.sort(l, new Comparator<Character>() {
      public int compare(Character a, Character b) {
        return -map.get(a).compareTo(map.get(b));
      }
    });
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < l.size(); i++) {
      int freq = map.get(l.get(i));
      for (int j = 0; j < freq; j++) {
        sb.append(l.get(i));
      }
    }
    return sb.toString();
  }

  public String hintBullandCow(String s, String t) {
    if (s == null || t == null) return null;
    int bull = 0, cow = 0;
    int[] map = new int[10];
    for (int i = 0; i < s.length(); i++) {
      int sc = s.charAt(i) - '0';
      int tc = t.charAt(i) - '0';
      if (sc == tc) bull++;
      else {
        if (map[sc] < 0) {cow++;}
        if (map[tc] > 0) {cow++;}
        map[sc]++;
        map[tc]--;
      }
    }
    return bull + "A" + cow + "B";
  }

  public boolean wordPattern(String pattern, String str) {
    if (pattern == null || str == null || pattern.isEmpty() || str.isEmpty()) return false;
    String[] elem = str.split(" ");
    if (pattern.length() != elem.length) return false;
    HashMap<String, Character> map = new HashMap<String, Character>();
    int[] count = new int[256];
    for (int i = 0; i < elem.length; i++) {
      char p = pattern.charAt(i);
      String word = elem[i];
      if (!map.containsKey(word) && count[p] ==0) {
        map.put(word, p);
        count[p]++;
      } else if (!(map.containsKey(word) && map.get(word) == p)) {
        return false;
      }
    }
    return true;
  }

  public boolean isomorphic(String s, String t) {
    if (s == null || t == null || s.length() != t.length()) return false;
    HashMap<Character, Character> map = new HashMap<Character, Character>();
    HashSet<Character> set = new HashSet<Character>();
    for (int i = 0; i < s.length(); i++) {
      char c1 = s.charAt(i);
      char c2 = t.charAt(i);
      if (!map.containsKey(c1)) {
        if (!set.contains(c2)) {
          map.put(c1, c2);
          set.add(c2);
        } else {
          return false;
        }
      } else {
        if (c2 != map.get(c1)) return false;
      }
    }
    return true;
  }

  public int countPrimes(int n) {
    if (n <= 2) return 0;
    List<Integer> primes = new ArrayList<Integer>();
    for (int i = 2; i < n; i++) {
      if (i == 2) primes.add(2);
      else {
        boolean isprime = true;
        for (Integer k : primes) {
          if (k * k >= n) break; // trick 1, prune the candidates
          if (i % k == 0) {
            isprime = false;
            break;
          }
        }
        if (isprime) primes.add(i);
      }
    }
    return primes.size();
  }

  public int countPrimesFast(int n) {
    if (n <= 2) return 0;
    boolean[] isPrime = new boolean[n];
    for (int i = 2; i < n; i++) isPrime[i] = true;
    for (int i = 2; i * i < n; i++) {
      if (!isPrime[i]) continue;
      for (int j = 2; i * j < n; j++) {
        isPrime[i * j] = false;
      }
    }
    int count = 0;
    for (int i = 0; i < n; i++) {
      if (isPrime[i]) count++;
    }
    return count;
  }

  public int islandPerimeter(int[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int m = grid.length, n = grid[0].length;
    int length = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] != 0) {
          if (i == 0 || grid[i-1][j] == 0) length++;
          if (i == m-1 || grid[i+1][j] == 0) length++;
          if (j == 0 || grid[i][j-1] == 0) length++;
          if (j == n-1 || grid[i][j+1] == 0) length++;
        }
      }
    }
    return length;
  }

  public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i = 0; i < A.length; i++) {
      for (int j = 0; j < B.length; j++) {
        int sum = A[i] + B[j];
        if (!map.containsKey(sum)) map.put(sum, 0);
        map.put(sum, map.get(sum)+1);
      }
    }
    int cnt = 0;
    for (int i = 0; i < C.length; i++) {
      for (int j = 0; j < D.length; j++) {
        int lookup = 0 - (C[i] + D[j]);
        if (map.containsKey(lookup)) cnt += map.get(lookup);
      }
    }
    return cnt;
  }

  public List<String> findRepeatedDnaSeq(String seq) {
    List<String> ret = new ArrayList<String>();
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    if (seq == null || seq.isEmpty()) return ret;
    for (int i = 0; i + 10 <= seq.length(); i++) {
      String substr = seq.substring(i, i+10);
      if (!map.containsKey(substr)) map.put(substr, 0);
      map.put(substr, map.get(substr) + 1);
    }
    for (String key : map.keySet()) {
      if (map.get(key) > 1) ret.add(key);
    }
    return ret;
  }

  public static void main(String[] args) {
    int[][] arr = {{-16,0},{16, 0}, {16, 0}};
    Hash test = new Hash();
    String s = "aabbcc";
    String p = "abc";
    String[] strs = {"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"};
    String[] dict = {"dear", "door", "cart", "cake"};
    ValidWordAbbr vwa = new ValidWordAbbr(dict);
    String[] words = {"practice", "makes", "perfect", "coding", "makes"};
    System.out.println(test.shortestWordDistanceOneCall(words, "makes", "coding"));
    int[] array = {-2,-1,2,1};
    int[][] grid=  {{0,1,0,0},
    {1,1,1,0},
      {0,1,0,0},
        {1,1,0,0}};
    System.out.println(test.islandPerimeter(grid));
  }
}

class Twitter {
  private HashMap<Integer, HashSet<Integer>> userToPost;
  private HashMap<Integer, HashSet<Integer>> follow;
  private HashMap<Integer, Integer> postTime;
  private int timestamp = 0;
  /** Initialize your data structure here. */
  public Twitter() {
    userToPost = new HashMap<Integer, HashSet<Integer>>();
    follow = new HashMap<Integer, HashSet<Integer>>();
    postTime = new HashMap<Integer, Integer>();
  }

  /** Compose a new tweet. */
  public void postTweet(int userId, int tweetId) {
    if (!userToPost.containsKey(userId)) userToPost.put(userId, new HashSet<Integer>());
    userToPost.get(userId).add(tweetId);
    postTime.put(tweetId, timestamp++);
  }

  /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
  public List<Integer> getNewsFeed(int userId) {
    List<Integer> ret = new LinkedList<Integer>();
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
      public int compare(Integer a, Integer b) {
        return postTime.get(a).compareTo(postTime.get(b));
      }
    });
    if (userToPost.containsKey(userId)) {
      for (Integer post : userToPost.get(userId)) {
        if (pq.size() < 10) pq.add(post);
        else {
          if (postTime.get(post) > postTime.get(pq.peek())) {
            pq.poll();
            pq.add(post);
          }
        }
      }
    }
    if (follow.containsKey(userId)) {
      HashSet<Integer> followee = follow.get(userId);
      for (Integer f : followee) {
        if (!userToPost.containsKey(f)) continue;
        for (Integer post : userToPost.get(f)) {
          if (pq.size() < 10) pq.add(post);
          else {
            if (postTime.get(post) > postTime.get(pq.peek())) {
              pq.poll();
              pq.add(post);
            }
          }
        }
      }
    }
    for (Integer post : pq) ret.add(post);
    Collections.sort(ret, new Comparator<Integer>() {
      public int compare(Integer a, Integer b) {
        return -postTime.get(a).compareTo(postTime.get(b));
      }
    });
    return ret;
  }

  /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
  public void follow(int followerId, int followeeId) {
    if (followerId == followeeId) return;
    if (!follow.containsKey(followerId)) {
      follow.put(followerId, new HashSet<Integer>());
    }
    follow.get(followerId).add(followeeId);
  }

  /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
  public void unfollow(int followerId, int followeeId) {
    if (follow.containsKey(followerId)) {
      follow.get(followerId).remove(followeeId);
    }
  }
}

class Logger {
  private HashMap<String, Integer> messageToTime;
  /** Initialize your data structure here. */
  public Logger() {
    messageToTime = new HashMap<String, Integer>();
  }

  /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
   If this method returns false, the message will not be printed.
   The timestamp is in seconds granularity. */
  public boolean shouldPrintMessage(int timestamp, String message) {
    if (!messageToTime.containsKey(message)) {
      messageToTime.put(message, timestamp);
      return true;
    } else {
      Integer prev = messageToTime.get(message);
      if (timestamp - prev < 10) return false;
      else {
        messageToTime.put(message, timestamp);
        return true;
      }
    }
  }
}

// This set does not allow duplicates
class RandomizedSet {
  private ArrayList<Integer> list; // value
  private HashMap<Integer, Integer> map;

  /** Initialize your data structure here. */
  public RandomizedSet() {
    map = new HashMap<Integer, Integer>();
    list = new ArrayList<Integer>();
  }

  /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
  public boolean insert(int val) {
    if (map.containsKey(val)) {
      return false;
    }
    else {
      map.put(val, list.size());
      list.add(val);
      return true;
    }
  }

  /** Removes a value from the set. Returns true if the set contained the specified element. */

  public boolean remove(int val) {
    if (map.containsKey(val)) {
      int pos = map.remove(val);
      int size = list.size();
      if (pos == size-1) {
        list.remove(size - 1);
      } else {
        int last = list.remove(size-1);
        list.set(pos, last);
        map.put(last, pos);
      }
      return true;
    }
    return false;
  }

  /** Get a random element from the set. */
  public int getRandom() {
    if (list.isEmpty()) return -1;
    if (list.size() == 1) return list.get(0);
    return list.get(new Random().nextInt(list.size()));
  }

}

class RandomizedCollection {

  private ArrayList<Integer> list;
  private HashMap<Integer, List<Integer>> index;

  /** Initialize your data structure here. */
  public RandomizedCollection() {
    list = new ArrayList<Integer>();
    index = new HashMap<Integer, List<Integer>>();
  }

  /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
  public boolean insert(int val) {
    list.add(val);
    if (index.containsKey(val)) {
      index.get(val).add(list.size()-1);
      return false;
    } else {
      List<Integer> l = new ArrayList<Integer>();
      l.add(list.size()-1);
      index.put(val, l);
      return true;
    }
  }

  /** Removes a value from the collection. Returns true if the collection contained the specified element. */
  public boolean remove(int val) {
    if (!index.containsKey(val)) return false;
    int pos = index.get(val).remove(0);
    if (index.get(val).isEmpty()) {
      index.remove(val);
    }
    if (pos == list.size()-1) {
      list.remove(pos);
    } else {
      int elem = list.remove(list.size()-1);
      list.set(pos, elem);
      List<Integer> occurrence = index.get(elem);
      for (int i = 0; i < occurrence.size(); i++) {
        if (occurrence.get(i) == list.size()) {
          occurrence.set(i, pos);
          break;
        }
      }
    }
    return true;
  }

  /** Get a random element from the collection. */
  public int getRandom() {
    return list.get(new Random().nextInt(list.size()));
  }
}

class TwoSum {
  private ArrayList<Integer> data;
  private HashSet<Integer> sum;

  public TwoSum() {
    data = new ArrayList<Integer>();
    sum = new HashSet<Integer>();
  }
  // Add the number to an internal data structure.
  public void add(int number) {
    if (data.isEmpty()) {
      data.add(number);
    } else {
      data.add(number);
      for (int i = 0; i < data.size()-1; i++) {
        sum.add(data.get(i) + number);
      }
    }
  }

  // Find if there exists any pair of numbers which sum is equal to the value.
  public boolean find(int value) {
    return sum.contains(value);
  }
}

class TwoSumBetter {
  private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

  public void add(int number) {
    if (!map.containsKey(number)) map.put(number, 1);
    else {
      map.put(number, map.get(number)+1);
    }
  }
  public boolean find(int value) {
    for (Integer key : map.keySet()) {
      int rest = value - key;
      if (map.containsKey(rest) && (key != rest || map.get(rest) > 1)) {
        return true;
      }
    }
    return false;
  }
}

class ValidWordAbbr {
  private HashMap<String, HashSet<String>> dict = new HashMap<String, HashSet<String>>();
  public ValidWordAbbr(String[] dictionary) {
    for (String s : dictionary) {
      String key = getAbbr(s);
      if (!dict.containsKey(key))
        dict.put(key, new HashSet<String>());
      dict.get(key).add(s);
    }
  }

  public boolean isUnique(String word) {
    String key = getAbbr(word);
    if (key == null) return false;
    if (dict.containsKey(key)) {
      HashSet<String> words = dict.get(key);
      return words.size() == 1 && words.contains(word);
    }
    return true;
  }
  private String getAbbr(String s) {
    if (s == null) return null;
    if (s.length() <= 2) return s;
    StringBuffer sb = new StringBuffer();
    sb.append(s.charAt(0));
    sb.append(Integer.toString(s.length()-2));
    sb.append(s.charAt(s.length()-1));
    return sb.toString();
  }
}

class WordDistance {
  private HashMap<String, List<Integer>> dict;
  public WordDistance(String[] words) {
    dict = new HashMap<String, List<Integer>>();
    for (int i = 0; i < words.length; i++) {
      if (!dict.containsKey(words[i])) dict.put(words[i], new LinkedList<Integer>());
      dict.get(words[i]).add(i);
    }
  }

  public int shortest(String word1, String word2) {
    if (!dict.containsKey(word1) || !dict.containsKey(word2)) return -1;
    if (word1.equals(word2)) return 0;
    List<Integer> pos1 = dict.get(word1);
    List<Integer> pos2 = dict.get(word2);
    int shortest = Integer.MAX_VALUE;
    int i = 0, j = 0;
    while (i < pos1.size() && j < pos2.size()) {
      shortest = Math.min(shortest, Math.abs(pos1.get(i) - pos2.get(j)));
      if (pos1.get(i) < pos2.get(j)) i++;
      else j++;
    }
    return shortest;
  }
}