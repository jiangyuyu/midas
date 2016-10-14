package midas.learning.algo;

import java.util.ArrayList;
import java.util.Arrays;
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

  public static void main(String[] args) {
    String s = "aabdcc";
    TwoPointers test = new TwoPointers();
    int[] nums = {0, 1, 2};
    System.out.println(test.threeSumClosest(nums, 0));
  }
}
