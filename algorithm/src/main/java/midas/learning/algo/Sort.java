package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import midas.data.Interval;
import midas.data.TreeNode;

public class Sort {
  public String largestNumber(int[] nums) {
    if (nums == null) return null;
    if (nums.length == 0) return "";
    String[] strs = new String[nums.length];
    for (int i = 0; i < nums.length; i++) {
      strs[i] = Integer.toString(nums[i]);
    }
    Arrays.sort(strs, new Comparator<String>() {
      public int compare(String a, String b) {
        return -(a + b).compareTo(b + a);
      }
    });
    StringBuilder sb = new StringBuilder();
    boolean foundNonZero = false;
    for (int i = 0; i < strs.length; i++) {
      if (!foundNonZero && strs[i].equals("0")) continue;
      if (!strs[i].equals("0")) foundNonZero = true;
      sb.append(strs[i]);
    }
    if (sb.length() == 0) return "0";
    return sb.toString();
  }

  public boolean isAnagram(String s, String t) {
    if (s == null && t == null) return true;
    if (s == null || t == null || s.length() != t.length()) return false;
    int[] count = new int[256];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i)]++;
    }
    for (int i = 0; i < t.length(); i++) {
      count[t.charAt(i)]--;
    }
    for (int i = 0; i < 256; i++) {
      if (count[i] != 0) return false;
    }
    return true;
  }

  public List<Interval> insertInterval(List<Interval> intervals, Interval newInterval) {
    if (intervals == null || newInterval == null) return intervals;
    List<Interval> ret = new LinkedList<Interval>();
    for (int i = 0; i < intervals.size(); i++) {
      Interval cur = intervals.get(i);
      if (cur.end < newInterval.start) {
        ret.add(cur);
      } else if (cur.start > newInterval.end) {
        ret.add(newInterval);
        newInterval = cur;
      } else {
        int start = Math.min(cur.start, newInterval.start);
        int end = Math.max(cur.end, newInterval.end);
        newInterval = new Interval(start, end);
      }
    }
    ret.add(newInterval);
    return ret;
  }

  public List<Interval> mergeInterval(List<Interval> intervals) {
    if (intervals == null || intervals.size() <= 1) return intervals;
    Collections.sort(intervals, new Comparator<Interval>() {
      public int compare(Interval a, Interval b) {
        if (a.start < b.start) return -1;
        else if (a.start == b.start) return 0;
        return 1;
      }
    });
    List<Interval> ret = new LinkedList<Interval>();
    int i = 0;
    while (i < intervals.size()) {
      int currentStart = intervals.get(i).start, currentEnd = intervals.get(i).end;
      i++;
      while (i < intervals.size() && intervals.get(i).start <= currentEnd) {
        currentEnd = Math.max(currentEnd, intervals.get(i).end);
        i++;
      }
      ret.add(new Interval(currentStart, currentEnd));
    }
    return ret;
  }

  public void sortColors(int[] nums) {
    if (nums == null || nums.length == 0) return;
    int n = nums.length;
    int left = 0, right = n-1, cur = 0;
    while (cur <= right) {
      if (nums[cur] == 0) {
        swap(nums, left++, cur++);
      } else if (nums[cur] == 2) {
        swap(nums, cur, right--);
      } else {
        cur++;
      }
    }
  }
  private void swap(int[] nums, int l, int r) {
    int temp = nums[l];
    nums[l] = nums[r];
    nums[r] = temp;
  }

  public int maxGap(int[] nums) {
    if (nums == null || nums.length <= 1) return 0;
    int n = nums.length;
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      if (min > nums[i]) min = nums[i];
      if (max < nums[i]) max = nums[i];
    }
    int len = (max - min)/n + 1;
    int[] minInBucket = new int[(max-min)/len + 1];
    int[] maxInBucket = new int[(max-min)/len + 1];
    int[] countBucket = new int[(max-min)/len + 1];
    Arrays.fill(minInBucket, Integer.MAX_VALUE);
    Arrays.fill(maxInBucket, Integer.MIN_VALUE);
    for (int i = 0; i < n; i++) {
      int bucket = (nums[i] - min)/len;
      countBucket[bucket]++;
      if (nums[i] < minInBucket[bucket]) minInBucket[bucket] = nums[i];
      if (nums[i] > maxInBucket[bucket]) maxInBucket[bucket] = nums[i];
    }
    int maxgap = 0, curmin = 0, curmax = 0;
    int i = 0;
    for (i = 0; i < countBucket.length; i++) {
      if (countBucket[i] > 0) {
        curmax = maxInBucket[i];
        break;
      }
    }
    for (i = i+1; i < countBucket.length; i++) {
      if (countBucket[i] > 0) {
        curmin = minInBucket[i];
        maxgap = Math.max(maxgap, curmin - curmax);
        curmax = maxInBucket[i];
      }
    }
    return maxgap;
  }
  public int getHIndex(int[] citations) {
    if (citations == null || citations.length == 0) return 0;
    int n = citations.length;
    int[] counter = new int[n+1];
    for (int i = 0; i < n; i++) {
      if (citations[i] >= n) {
        counter[n]++;
      } else {
        counter[citations[i]]++;
      }
    }
    int hindex = 0;
    int sum = 0;
    for (int i = n; i >= 0; i--) {
      sum += counter[i];
      hindex = Math.max(hindex, Math.min(i, sum));
    }
    return hindex;
  }
  public int getHIndexSort(int[] citations) {
    if (citations == null || citations.length == 0) return 0;
    Arrays.sort(citations);
    int n = citations.length;
    int hindex = 0;
    for (int i = n-1; i >= 0; i--) {
      hindex = Math.max(hindex, Math.min(n-i, citations[i]));
    }
    return hindex;
  }

  public int getHIndexSorted(int[] citations) {
    if (citations == null || citations.length == 0) return 0;
    int n = citations.length;
    int l = 0, r = n-1;
    while (l < r) {
      int m = l + (r-l)/2;
      if (n - m == citations[m]) return n-m;
      else if (n - m < citations[m]) {
        r = m-1;
      } else {
        l = m+1;
      }
    }
    return n-l;
  }

  public void wiggleSortInPlace(int[] nums) {
    if (nums == null || nums.length == 0) return;
    int n = nums.length;
    for (int i = 1; i < n; i++) {
      if ((i%2 == 1 && nums[i-1] > nums[i]) || (i%2 == 0 && nums[i] > nums[i-1])) {
        int temp = nums[i-1];
        nums[i-1] = nums[i];
        nums[i] = temp;
      }
    }
  }

  public void wiggleSort2(int[] nums) {
    if (nums == null || nums.length <= 1) return;
    int n = nums.length;
    int[] temp = Arrays.copyOf(nums, n);
    Arrays.sort(temp);
    int j = n-1;
    for (int i = 1; i < n; i = i+2) {
      nums[i] = temp[j--];
    }
    for (int i = 0; i < n; i = i+2) {
      nums[i] = temp[j--];
    }
  }

  public int bestMeetingPoint(int[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
    List<Integer> xpos = new ArrayList<Integer>();
    List<Integer> ypos = new ArrayList<Integer>();
    int r = grid.length, c = grid[0].length;
    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {
        if (grid[i][j] == 1) {
          xpos.add(i);
          ypos.add(j);
        }
      }
    }
    int distance = 0;
    for (int i = 0; i < xpos.size(); i++) {
      distance += Math.abs(xpos.get(i) - xpos.get(xpos.size()/2));
    }
    Collections.sort(ypos);
    for (int i = 0; i < ypos.size(); i++) {
      distance += Math.abs(ypos.get(i) - ypos.get(ypos.size()/2));
    }
    return distance;
  }

  public boolean attendAllMeetings(Interval[] time) {
    if (time == null || time.length <= 1) return true;
    Arrays.sort(time, new Comparator<Interval>() {
      public int compare(Interval a, Interval b) {
        return a.start - b.start;
      }
    });
    for (int i = 1; i < time.length; i++) {
      if (time[i-1].end > time[i].start) return false;
    }
    return true;
  }

  public int minMeetingRoom(Interval[] time) {
    if (time == null || time.length == 0) return 0;
    Arrays.sort(time, new Comparator<Interval>() {
      public int compare(Interval a, Interval b) {
        return a.start - b.start;
      }
    });
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
    int count = 0;
    for (int i = 0; i < time.length; i++) {
      if (pq.isEmpty()) {
        count++;
        pq.add(time[i].end);
      } else if (time[i].start < pq.peek()) {
        count++;
        pq.add(time[i].end);
      } else {
        pq.poll();
        pq.add(time[i].end);
      }
    }
    return count;
  }

  public int[] intersectionHash(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null) return null;
    int m = nums1.length, n = nums2.length;
    if (m == 0 || n == 0) return new int[0];
    HashSet<Integer> set1 = new HashSet<Integer>();
    for (int i : nums1) set1.add(i);
    HashSet<Integer> intersect = new HashSet<Integer>();
    for (int i : nums2) {
      if (set1.contains(i) && !intersect.contains(i)) intersect.add(i);
    }
    int[] ret = new int[intersect.size()];
    int i = 0;
    for (int num : intersect) {
      ret[i++] = num;
    }
    return ret;
  }

  public int[] intersectMultiCopyHash(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null) return null;
    int m = nums1.length, n = nums2.length;
    HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
    for (int i : nums1) {
      if (!map1.containsKey(i)) map1.put(i, 0);
      map1.put(i, map1.get(i)+1);
    }
    int[] ret = new int[Math.min(m, n)];
    int k = 0;
    for (int i : nums2) {
      if (map1.containsKey(i) && map1.get(i) > 0) {
        ret[k++] = i;
        map1.put(i, map1.get(i)-1);
      }
    }
    return Arrays.copyOfRange(ret, 0, k);
  }

  public int[] intersectionSort(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null) return null;
    int m = nums1.length, n = nums2.length;
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int[] ret = new int[Math.min(m, n)];
    int i = 0, j = 0, k = 0;
    while (i < m && j < n) {
      if (nums1[i] == nums2[j]) {
        ret[k++] = nums1[i];
        i++;
        while (i < m && nums1[i] == nums1[i-1]) i++;
        j++;
        while (j < n && nums2[j] == nums2[j-1]) j++;
      } else if (nums1[i] < nums2[j]) {
        i++;
      } else {
        j++;
      }
    }
    return Arrays.copyOfRange(ret, 0, k);
  }

  public int[] intersectionMultiCopySort(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null) return null;
    int m = nums1.length, n = nums2.length;
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int i = 0, j = 0, k = 0;
    int[] ret = new int[Math.min(m, n)];
    while (i < m && j < n) {
      if (nums1[i] == nums2[j]) {
        ret[k++] = nums1[i];
        i++;
        j++;
      } else if (nums1[i] < nums2[j]) {
        i++;
      } else {
        j++;
      }
    }
    return Arrays.copyOfRange(ret, 0, k);
  }

  public int firstBadVersion(int n) {
    if (n <= 0) throw new IllegalArgumentException();
    int l = 1, r = n;
    while (l < r) {
      int m = l + (r-l)/2;
      if (isBadVersion(m)) {
        r = m;
      } else {
        l = m+1;
      }
    }
    if (!isBadVersion(l)) return -1;
    return l;
  }
  private boolean isBadVersion(int v) {
    return false;
  }
  private int guess(int i) {
    return 0;
  }

  public int guessNumber(int n) {
    if (n <= 0) return 0;
    int l = 1, r = n;
    while (l <= r) {
      int m = l + (r-l)/2;
      int temp = guess(m);
      if (temp == 0) return m;
      else if (temp < 0) r = m-1;
      else l = m+1;
    }
    return 0;
  }

  public int searchInsertPosition(int[] nums, int target) {
    if (nums == null) return -1;
    int n = nums.length;
    if (n == 0) return 0;
    int l = 0, r = n-1;
    while (l <= r) {
      int m = l + (r-l)/2;
      if (nums[m] < target) l = m+1;
      else if (nums[m] > target) r = m-1;
      else return m;
    }
    return l;
  }

  public boolean search2DMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
    int m = matrix.length, n = matrix[0].length;
    int l = 0, r = m*n-1;
    while (l <= r) {
      int mid = l + (r-l)/2;
      int row = mid/n, col = mid%n;
      if (matrix[row][col] == target) return true;
      else if (matrix[row][col] < target) l = mid+1;
      else r = mid-1;
    }
    return false;
  }

  public boolean search2DMatrixII(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
    int row = matrix.length, col = matrix[0].length;
    int i = 0, j = col-1;
    while (i < row && j >= 0) {
      if (matrix[i][j] == target) return true;
      else if (matrix[i][j] < target) i++;
      else j--;
    }
    return false;
  }

  public int countNodesCompleteTreeNaive(TreeNode root) {
    if (root == null) return 0;
    int left = countNodesCompleteTreeNaive(root.left);
    int right = countNodesCompleteTreeNaive(root.right);
    return left + right + 1;
  }
  public int countNodesCompleteTree(TreeNode root) {
    if (root == null) return 0;
    int lh = 0, rh = 0;
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
      return (1<<lh)-1;
    }
    return 1 + countNodesCompleteTree(root.left) + countNodesCompleteTree(root.right);
  }

  public int kthSmallestInBST(TreeNode root, int k) {
    if (root == null) throw new IllegalArgumentException();
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode cur = root;
    int i = 0;
    while (cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.push(cur);
        cur = cur.left;
      } else {
        cur = stack.pop();
        i++;
        if (i == k) return cur.val;
        cur = cur.right;
      }
    }
    return 0;
  }
  private int getNumberOfNodes(TreeNode root) {
    if (root == null) return 0;
    return getNumberOfNodes(root.left) + 1 + getNumberOfNodes(root.right);
  }
  public int kthSmallestInBSTLog(TreeNode root, int k) {
    if (root == null) throw new IllegalArgumentException();
    int leftcount = getNumberOfNodes(root.left);
    if (leftcount == k-1) return root.val;
    else if (leftcount < k-1) return kthSmallestInBST(root.right, k-leftcount-1);
    return kthSmallestInBST(root.left, k);
  }

  public int minLengthSubarray(int[] nums, int target) {
    if (nums == null) return 0;
    int n = nums.length;
    int minlen = n+1, left = 0, sum = 0;
    for (int right = 0; right < n; right++) {
      sum += nums[right];
      while (sum >= target) {
        minlen = Math.min(minlen, right-left + 1);
        sum = sum - nums[left];
        left++;
      }
    }
    if (minlen == n+1) return 0;
    return minlen;
  }

  public int minLengthSubarrayLog(int[] nums, int target) {
    if (nums == null || nums.length == 0) return 0;
    int n = nums.length;
    int[] sum = new int[n+1];
    for (int i = 1; i <= n; i++) {
      sum[i] = sum[i-1] + nums[i-1];
    }
    int minlen = n+1;
    for (int i = 1; i <= n; i++) {
      if (sum[i] >= target) {
        int diff = sum[i] - target;
        int index = mybinarySearch(sum, diff);
        minlen = Math.min(minlen, i - index);
      }
    }
    if (minlen == n+1) return 0;
    return minlen;
  }
  private int mybinarySearch(int[] nums, int target) {
    int l = 0, r = nums.length-1;
    while (l <= r) {
      int m = l + (r-l)/2;
      if (nums[m] == target) return m;
      else if (nums[m] < target) l = m+1;
      else r = m-1;
    }
    return r;
  }

  public int longestIncreasingSubsequence(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int n = nums.length;
    int[] f = new int[n];
    f[0] = 1;
    int max = 1;
    for (int i = 1; i < n; i++) {
      f[i] = 1;
      for (int j = i-1; j >= 0; j--) {
        if (nums[i] > nums[j]) f[i] = Math.max(f[i], f[j]+1);
      }
      max = Math.max(max, f[i]);
    }
    return max;
  }

  public int longestIncreasingSubsequenceLog(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    ArrayList<Integer> l = new ArrayList<Integer>();
    l.add(nums[0]);
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] < l.get(0)) l.set(0, nums[i]);
      else if (nums[i] > l.get(l.size()-1)) l.add(nums[i]);
      else {
        int left = 0, right = l.size()-1;
        while (left <= right) {
          int mid = left + (right - left)/2;
          if (l.get(mid) == nums[i]) {
            left = mid;
            break;
          }
          else if (l.get(mid) < nums[i]) left = mid+1;
          else right = mid-1;
        }
        l.set(left, nums[i]);
      }
    }
    return l.size();
  }

  public int[] twoSumSortedArray(int[] nums, int target) {
    if (nums == null || nums.length <= 1) return null;
    int[] ret = new int[2];
    Arrays.fill(ret, -1);
    int l = 0, r = nums.length-1;
    while (l < r) {
      if (nums[l] + nums[r] == target) {
        ret[0] = l+1;
        ret[1] = r+1;
        break;
      } else if (nums[l] + nums[r] < target) {
        l++;
      } else {
        r--;
      }
    }
    return ret;
  }

  public int closestValueInBST(TreeNode root, double target) {
    if (root == null) throw new IllegalArgumentException();
    double diff = Double.MAX_VALUE;
    int closest = 0;
    TreeNode cur = root;
    while (cur != null) {
      if (Math.abs(cur.val - target) < diff) {
        diff = Math.abs(cur.val - target);
        closest = cur.val;
      }
      if (cur.val < target) {
        cur = cur.right;
      } else if (cur.val > target){
        cur = cur.left;
      } else {
        break;
      }
    }
    return closest;
  }

  public double pow(double x, int n) {
    if (x == 0) return 0;
    if (x == 1 || n == 0) return 1;
    boolean isNeg = n < 0;
    double ret = 1.0;
    if (n == Integer.MIN_VALUE) {
      n = Integer.MAX_VALUE;
      ret = ret * x;
    } else {
      n = n < 0 ? -n : n;
    }
    while (n > 0) {
      if (n %2 == 0) {
        x = x * x;
        n = n/2;
      } else {
        ret = ret * x;
        n--;
      }
    }
    if (isNeg) return 1.0/ret;
    return ret;
  }

  public int findMinRotated(int[] nums) {
    if (nums == null || nums.length == 0) throw new IllegalArgumentException();
    int l = 0, r = nums.length-1;
    while (l < r) {
      int m = l + (r - l)/2;
      if (nums[m] < nums[r]) {
        r = m;
      } else if (nums[m] > nums[r]) {
        l = m+1;
      }
    }
    return nums[l];
  }
  public int findMinRotatedDup(int[] nums) {
    if (nums == null || nums.length == 0) throw new IllegalArgumentException();
    int n = nums.length;
    int l = 0, r = n-1;
    while (l < r) {
      int m = l + (r-l)/2;
      if (nums[m] < nums[r]) {
        r = m;
      } else if (nums[m] > nums[r]) {
        l = m+1;
      } else {
        r--;
      }
    }
    return nums[l];
  }

  public int searchRotated(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int n = nums.length;
    int left = 0, right = n-1;
    while (left <= right) {
      int mid = left + (right - left)/2;
      if (nums[mid] == target) return mid;
      else if (nums[mid] < nums[right]) {
        if (target > nums[mid] && target <= nums[right]) {
          left = mid+1;
        } else {
          right = mid-1;
        }
      } else {
        if (target >= nums[left] && target < nums[mid]) {
          right = mid-1;
        } else {
          left = mid+1;
        }
      }
    }
    return -1;
  }

  public int divide(int dividend, int divisor) {
    if (divisor == 0) return Integer.MAX_VALUE;
    boolean isneg = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);
    if (divisor < 0) {
      if (divisor == Integer.MIN_VALUE) {
        if (dividend == Integer.MIN_VALUE) return 1;
        else return 0;
      } else {
        divisor = -divisor;
      }
    }
    int ret = 0;
    if (dividend < 0) {
      if (dividend == Integer.MIN_VALUE) {
        if (divisor == 1) {
          if (!isneg) return Integer.MAX_VALUE;
          else return Integer.MIN_VALUE;
        } else {
          dividend = -(dividend + divisor);
          ret++;
        }
      } else {
        dividend = -dividend;
      }
    }
    while (dividend >= divisor) {
      int x = divisor, k = 1;
      while ((x << 1) > x && (x << 1) <= dividend) {
        x = x << 1;
        k = k << 1;
      }
      dividend -= x;
      ret += k;
    }
    if (isneg) return -ret;
    return ret;
  }

  public int sqrt(int num) {
    if (num < 0) throw new IllegalArgumentException();
    if (num <= 1) return num;
    int l = 1, r = num;
    while (l <= r) {
      int m = l + (r - l)/2;
      if (m > Integer.MAX_VALUE/m) r = m-1;
      else {
        if (m * m < num) l = m+1;
        else if (m * m > num) r = m-1;
        else return m;
      }
    }
    return r;
  }

  public int findPeakLog(int[] array) {
    if (array == null || array.length == 0) return -1;
    int n = array.length;
    if (n == 1) return 0;
    int l = 0, r = n-1;
    while (l <= r) {
      int m = l + (r-l)/2;
      if ((m == 0||array[m] > array[m-1]) && (m == n-1 || array[m] > array[m+1])) return m;
      else if (m > 0 && array[m] < array[m-1]) r = m-1;
      else if (m < n-1 && array[m] < array[m+1]) l = m+1;
    }
    return -1;
  }

  public int[] searchRange(int[] nums, int target) {
    if (nums == null) return null;
    int[] ret = new int[2];
    int l = 0, n = nums.length, r = n-1;
    Arrays.fill(ret, -1);
    while (l <= r) {
      int m = l + (r-l)/2;
      if (nums[m] < target) l = m+1;
      else if (nums[m] > target) r = m-1;
      else {
        ret[0] = m;
        r = m-1;
      }
    }
    l = 0; r = n-1;
    while (l <= r) {
      int m = l + (r-l)/2;
      if (nums[m] < target) l = m+1;
      else if (nums[m] > target) r = m-1;
      else {
        ret[1] = m;
        l = m+1;
      }
    }
    return ret;
  }

  public int binarySearch(int[] a, int target) {
    int l = 0, r = a.length-1;
    while (l <= r) {
      int m = l + (r-l)/2;
      if (a[m] < target) l = m+1;
      else if (a[m] > target) r = m-1;
      else return m;
    }
    return -1; // l < r
  }

  public boolean isSubsequence(String s, String t) {
    if (s == null || t == null || s.length() > t.length()) return false;
    return isSubsequenceHelper(s, 0, t, 0);
  }
  private boolean isSubsequenceHelper(String s, int i, String t, int j) {
    if (i == s.length()) return true;
    if (i < s.length() && j >= t.length()) return false;
    for (int k = j; k < t.length(); k++) {
      if (s.charAt(i) == t.charAt(k)) {
        if (isSubsequenceHelper(s, i+1, t, k+1)) return true;
      }
    }
    return false;
  }
  public boolean isSubsequenceQueue(String s, String t) {
    if (s == null || t == null || s.length() > t.length()) return false;
    int i = 0, j = 0;
    int m = s.length(), n = t.length();
    while (i < m && j < n) {
      if (s.charAt(i) == t.charAt(j)) i++;
      j++;
    }
    if (i == m) return true;
    return false;
  }

  public int minAreaBlockBlackPixel(char[][] image, int x, int y) {
    if (image == null || image.length == 0 || image[0].length == 0) return 0;
    int m = image.length, n = image[0].length;
    int left = y, right = y, up = x, down = x;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (image[i][j] == '1') {
          left = Math.min(left, j);
          right = Math.max(right, j);
          up = Math.min(up, i);
          down = Math.max(down, i);
        }
      }
    }
    return (right - left + 1) * (down - up + 1);
  }

  public int kthSmallestInSortedMatrix(int[][] matrix, int k) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) throw new IllegalArgumentException();
    int row = matrix.length, col = matrix[0].length;
    PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return -o1.compareTo(o2);
      }
    });
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (pq.size() < k) pq.add(matrix[i][j]);
        else if (pq.peek() < matrix[i][j]) continue;
        else {
          pq.poll();
          pq.add(matrix[i][j]);
        }
      }
    }
    return pq.peek();
  }
  public int kthSmallestSortedMatrixBinarySearch(int[][] matrix, int k) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) throw new IllegalArgumentException();
    int m = matrix.length, n = matrix[0].length;
    int left = matrix[0][0], right = matrix[m-1][n-1];
    while (left < right) {
      int mid = left + (right - left)/2;
      int count = upperbound(matrix, mid);
      if (count < k) {
        left = mid+1;
      } else if (count >= k) {
        right = mid;
      }
    }
    return left;
  }
  private int upperbound(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int i = m-1, j = 0;
    int count = 0;
    while (i >= 0 && j < n) {
      if (matrix[i][j] <= target) {
        count += i+1;
        j++;
      } else {
        i--;
      }
    }
    return count;
  }

  private int upperboundAlter(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int i = 0, j = n-1;
    int ret = 0;
    while (i < m && j >= 0) {
      if (matrix[i][j] <= target) {
        ret += j+1;
        i++;
      } else {
        j--;
      }
    }
    return ret;
  }

  public boolean isPerfectSquare(int num) {
    if (num < 0) return false;
    if (num <= 1) return true;
    int l = 1, r = num-1;
    while (l <= r) {
      int m = l + (r-l)/2;
      if (m > Integer.MAX_VALUE/m) {
        r = m-1;
      } else {
        if (m * m == num) return true;
        else if (m * m < num) {
          l = m+1;
        } else {
          r = m-1;
        }
      }
    }
    return false;
  }
  public int findDuplicateNumber(int[] nums) {
    if (nums == null || nums.length <= 1) throw new IllegalArgumentException();
    int n = nums.length;
    int left = 1, right = n-1;
    while (left < right) {
      int mid = left + (right - left)/2;
      int count = 0;
      for (int i = 0; i < n; i++) {
        if (nums[i] <= mid) count++;
      }
      if (count <= mid) left = mid+1;
      else if (count > mid) right = mid;
    }
    return left;
  }

  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null) throw new IllegalArgumentException();
    int m = nums1.length, n = nums2.length;
    int x = findKth(nums1, 0, nums2, 0, (m+n-1)/2);
    if ((m+n)%2 == 1) return x;
    int y = findKth(nums1, 0, nums2, 0, (m+n)/2);
    return (x+y)/2.0;
  }

  public int findKth(int[] a, int i, int[] b, int j, int pos) {
    if (i >= a.length) return b[j+pos];
    if (j >= b.length) return a[i+pos];
    if (pos == 0) return a[i] < b[j] ? a[i] : b[j];
    int k = (pos-1)/2;
    int x = Math.min(i+k, a.length-1);
    int y = Math.min(j+k, b.length-1);
    if (a[x] < b[y]) return findKth(a, x+1, b, j, pos - (x-i+1));
    return findKth(a, i, b, y+1, pos - (y-j+1));
  }

  public static void main(String[] args) {
    Sort test = new Sort();
    int[] nums = {2, 3, 4};
    int[][] matrix = {{1, 5, 9}, {12, 11, 13}, {10, 13, 15}};
    System.out.println(Arrays.deepToString(matrix));
  }
}
