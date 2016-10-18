package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class TwoPointers {
  public int lengthOfLongestSubstringWORepeat(String s) {
    if (s == null || s.length() == 0) return 0;
    int[] count = new int[256];
    int left = 0, maxlen = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (count[c] == 0) {
        count[c]++;
      } else {
        maxlen = Math.max(maxlen, i - left);
        while (left < i && s.charAt(left) != c) {
          count[s.charAt(left)]--;
          left++;
        }
        if (s.charAt(left) == c) left++;
      }
    }
    maxlen = Math.max(maxlen, s.length()-left);
    return maxlen;
  }

  public String reverseString(String s) {
    if (s == null || s.length() <= 1) return s;
    int n = s.length();
    char[] arr = s.toCharArray();
    int left = 0, right = n-1;
    while (left < right) {
      char temp = arr[left];
      arr[left] = arr[right];
      arr[right] = temp;
      left++;
      right--;
    }
    return new String(arr);
  }

  public List<List<Integer>> threeSum(int[] nums) {
    if (nums == null || nums.length < 3) return new ArrayList<List<Integer>>();
    int n = nums.length;
    Arrays.sort(nums);
    List<List<Integer>> ret = new ArrayList<List<Integer>>();

    for (int i = 0; i < n; i++) {
      if (i > 0 && nums[i] == nums[i-1]) continue;
      int target = -nums[i];
      int left = i+1, right = n-1;
      while (left < right) {
        if (nums[left] + nums[right] < target) left++;
        else if (nums[left] + nums[right] > target) right--;
        else {
          List<Integer> l = new ArrayList<Integer>();
          l.add(nums[i]);
          l.add(nums[left]);
          l.add(nums[right]);
          ret.add(l);
          left++;
          while (left < right && nums[left] == nums[left-1]) left++;
        }
      }
    }
    return ret;
  }

  public int threeSumClosest(int[] nums, int target) {
    if (nums == null || nums.length < 3) throw new IllegalArgumentException();
    int n = nums.length;
    Arrays.sort(nums);
    int closest = 0, mindiff = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
      int temp = target - nums[i];
      int left = i+1, right = n-1;
      while (left < right) {
        int sum = nums[left] + nums[right];
        if (sum == temp) return target;
        else if (sum < temp) {
          mindiff = Math.abs(mindiff) < temp-sum? mindiff: sum-temp;
          left++;
        } else {
          mindiff = Math.abs(mindiff) < sum - temp? mindiff : sum-temp;
          right--;
        }
      }
    }
    return target + mindiff;
  }

  public int threeSumSmaller(int[] nums, int target) {
    if (nums == null || nums.length < 3) return 0;
    int n = nums.length;
    Arrays.sort(nums);
    int ret = 0;
    for (int i = 0; i < n; i++) {
      int left = i+1, right = n-1;
      while (left < right) {
        int sum = nums[i] + nums[left] + nums[right];
        if (sum >= target) {
          right--;
        } else {
          ret += right - left;
          left++;
        }
      }
    }
    return ret;
  }

  public List<List<Integer>> fourSum(int[] nums, int target) {
    if (nums == null || nums.length < 4) return new ArrayList<List<Integer>>();
    Arrays.sort(nums);
    int n = nums.length;
    List<List<Integer>> ret = new ArrayList<List<Integer>>();
    for (int i = 0; i < n; i++) {
      if (i > 0 && nums[i] == nums[i-1]) continue;
      for (int j = i+1; j < n; j++) {
        if (j > i+1 && nums[j] == nums[j-1]) continue;
        int sum = target - nums[i] - nums[j];
        int left = j+1, right = n-1;
        while (left < right) {
          int cursum = nums[left] + nums[right];
          if (cursum < sum) left++;
          else if (cursum > sum) right--;
          else {
            List<Integer> l = new ArrayList<Integer>();
            l.add(nums[i]);
            l.add(nums[j]);
            l.add(nums[left]);
            l.add(nums[right]);
            ret.add(l);
            left++;
            while (left < right && nums[left] == nums[left-1]) left++;
          }
        }
      }
    }
    return ret;
  }

  public void moveZeros(int[] nums) {
    if (nums == null || nums.length <= 1) return;
    int left = 0, right = 0, n = nums.length;
    for (right = 0; right < n; right++) {
      if (nums[right] != 0) {
        nums[left++] = nums[right];
      }
    }
    while (left < n) {
      nums[left++] = 0;
    }
  }

  public int containerWithMostWater(int[] height) {
    if (height == null || height.length <= 1) return 0;
    int n = height.length;
    int left = 0, right = n-1;
    int max = 0;
    while (left < right) {
      int h = height[left] < height[right]? height[left] : height[right];
      max = max >= h* (right - left) ? max : h* (right - left);
      if (height[left] <= height[right]) {
        left++;
      } else right--;
    }
    return max;
  }

  public int removeDuplicatesOneCopy(int[] nums) {
    if (nums == null || nums.length <= 1) return nums.length;
    int n = nums.length, left = 1, right = 0;
    for (right = 1; right < n; right++) {
      if (nums[right] != nums[right-1]) {
        nums[left++] = nums[right];
      }
    }
    return left;
  }

  public int removeDuplicatesTwoCopy(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int n = nums.length;
    int left = 0, right = 0, count = 0;
    for (right = 0; right < n; right++) {
      if (right > 0 && nums[right] == nums[right-1]) {
        if (count < 2) {
          count++;
          nums[left++] = nums[right];
        }
      } else {
        count = 1;
        nums[left++] = nums[right];
      }
    }
    return left;
  }

  public int removeElement(int[] nums, int target) {
    if (nums == null) throw new IllegalArgumentException();
    int left = 0, right = 0;
    for (right = 0; right < nums.length; right++) {
      if (nums[right] != target) {
        nums[left++] = nums[right];
      }
    }
    return left;
  }

  public void mergeSort(int[] nums1, int m, int[] nums2, int n) {
    if (nums1 == null || nums2 == null) return;
    int i = m-1, j = n-1, k = m+n-1;
    while (i >= 0 && j >= 0) {
      if (nums1[i] <= nums2[j]) {
        nums1[k--] = nums2[j--];
      } else {
        nums1[k--] = nums1[i--];
      }
    }
    while (j >= 0) {
      nums1[k--] = nums2[j--];
    }
  }

  public int strStr(String haystack, String needle) {
    if (haystack == null || needle == null) return -1;
    if (needle.length() == 0) return 0;
    int m = haystack.length(), n = needle.length();
    for (int i = 0; i <= m-n; i++) {
      int j = 0;
      for (j = 0; j < n; j++) {
        if (haystack.charAt(i+j) != needle.charAt(j)) break;
      }
      if (j == n) return i;
    }
    return -1;
  }

  public int trapWater(int[] height) {
    if (height == null) return 0;
    int n = height.length;
    if (n <= 1) return 0;
    int left = 0, right = n-1, ret = 0;
    while (left < right) {
      int min = height[left] < height[right]? height[left] : height[right];
      if (height[left] < height[right]) {
        left++;
        while (left < right && height[left] < min) {
          ret += min - height[left];
          left++;
        }
      } else {
        right--;
        while (left < right && height[right] < min) {
          ret += min - height[right];
          right--;
        }
      }
    }
    return ret;
  }

  public boolean isPalindrome(String s) {
    if (s == null) return false;
    int n = s.length();
    if (n <= 1) return true;
    s = s.toLowerCase();
    int left = 0, right = n-1;
    while (left < right) {
      while (left < right && !Character.isAlphabetic(s.charAt(left)) && !Character.isDigit(s.charAt(left))) left++;
      while (left < right && !Character.isAlphabetic(s.charAt(right)) && !Character.isDigit(s.charAt(right))) right--;
      if (s.charAt(left) != s.charAt(right)) return false;
      left++;
      right--;
    }
    return true;
  }

  public String minWindowSubstring(String s, String t) {
    int m = s.length(), n = t.length();
    int[] cs = new int[256], ct = new int[256];
    for (int i = 0; i < n; i++) {
      ct[t.charAt(i)]++;
    }
    int left = 0, right = 0, total = 0;
    int minlen = m+1;
    String ret = "";
    for (right = 0; right < m; right++) {
      char c = s.charAt(right);
      cs[c]++;
      if (ct[c] > 0 && cs[c] <= ct[c]) total++;
      if (total == n) {
        while(left < right) {
          char cur = s.charAt(left);
          if (cs[cur] > ct[cur]) {
            cs[cur]--;
            left++;
          } else {
            break;
          }
        }
        if (minlen > right-left+1) {
          minlen = right-left+1;
          ret = s.substring(left, right+1);
        }
        cs[s.charAt(left)]--;
        total--;
        left++;
      }
    }
    return ret;
  }

  public List<Integer> containsAllWords(String s, String[] words) {
    if (s == null || words == null || s.length() == 0 || words.length == 0) return new ArrayList<Integer>();
    int len = words[0].length();
    List<Integer> ret = new ArrayList<Integer>();
    HashMap<String, Integer> dict = new HashMap<String, Integer>();
    for (String w : words) {
      if (!dict.containsKey(w)) dict.put(w, 0);
      dict.put(w, dict.get(w)+1);
    }
    HashMap<String, Integer> temp = new HashMap<String, Integer>();
    for (int i = 0; i < s.length(); i++) {
      int total = 0;
      temp.clear();
      for (int j = i; j<= s.length()-len; j = j+len) {
        String str = s.substring(j, j+len);
        if (!dict.containsKey(str)) break;
        else {
          if (dict.get(str) > 0 && (!temp.containsKey(str) || dict.get(str) > temp.get(str))) {
            if (!temp.containsKey(str)) temp.put(str, 0);
            temp.put(str, temp.get(str) + 1);
            total++;
          } else {
            break;
          }
        }
        if (total == words.length) {
          ret.add(i);
        }
      }
    }
    return ret;
  }

  public int longestSubstringWithTwoDistinctChar(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    HashMap<Character, Integer> map = new HashMap<Character, Integer>();
    int left = 0, right = 0, max = 0;
    for (right = 0; right < n; right++) {
      char c = s.charAt(right);
      if (!map.containsKey(c)) map.put(c, 0);
      map.put(c, map.get(c) + 1);
      if (map.size() > 2) {
        while (map.size() > 2) {
          char c1 = s.charAt(left);
          if (map.containsKey(c1)) {
            if (map.get(c1) == 1) map.remove(c1);
            else {
              map.put(c1, map.get(c1)-1);
            }
          }
          left++;
        }
      }
      max = Math.max(max, right - left+1);
    }
    return max;
  }

  public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
    if (nums == null || nums.length == 0) return null;
    int n = nums.length;
    int[] ret = new int[n];
    int i = 0, j = n-1, k = 0;
    while (i <= j) {
      if (a > 0) {
        ret[n-1-k] = cal(nums[i], a, b, c) < cal(nums[j], a, b, c) ? cal(nums[j--], a, b, c) : cal(nums[i++], a, b, c);
      } else {
        ret[k] = cal(nums[i], a, b, c) < cal(nums[j], a, b, c) ? cal(nums[i++], a, b, c) : cal(nums[j--], a, b, c);
      }
      k++;
    }
    return ret;
  }
  private int cal(int x, int a, int b, int c) {
    return a * x * x + b * x + c;
  }

  public static void main(String[] args) {
    TwoPointers test = new TwoPointers();
    int[] nums = {-4, -2, 2, 4};
    String s = "eceba";
    String t = "ABC";
    String[] words = {"word","good","best","good"};
    System.out.println(Arrays.toString(test.sortTransformedArray(nums, 1, 3, 5)));
  }
}
