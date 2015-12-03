package midas.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by xli1 on 11/6/15.
 */
public class TwoPointers {
  public boolean flowerPlant(boolean[] spots, int newPlants) {
    if (spots == null || spots.length == 0) return false;
    int capacity = 0;
    for (int i = 0; i < spots.length; i++) {
      if (!spots[i] && (i == 0 || !spots[i-1]) && (i == spots.length-1 || !spots[i+1]))
        capacity++;
    }
    return capacity >= newPlants;
  }

  // Assume the array is sorted.
  public int[] twoSumSorted(int[] nums, int target) {
    if (nums == null || nums.length < 2) return null;
    int left = 0, right = nums.length - 1;
    int[] res = null;
    while (left != right) {
      int sum = nums[left] + nums[right];
      if (sum == target) {
        res = new int[2];
        res[0] = left + 1;
        res[1] = right + 1;
      } else if (sum < target) {
        left++;
      } else {
        right++;
      }
    }
    return res;
  }

  public int lengthOfLongestSubstring(String s) {
    int maxlen = 0;
    if (s == null || s.length() == 0) return maxlen;

    int[] lastIndex = new int[256];
    Arrays.fill(lastIndex, -1);
    int left = 0;
    for (int right = 0; right < s.length(); right++) {
      char curChar = s.charAt(right);
      if (lastIndex[curChar] == -1) {
        lastIndex[curChar] = right;
      } else {
        maxlen = Math.max(maxlen, right-left);
        while (left <= lastIndex[curChar]) {
          lastIndex[s.charAt(left)] = -1;
          left++;
        }
        lastIndex[curChar] = right;
      }
    }
    maxlen = Math.max(maxlen, s.length()-left);
    return maxlen;
  }

  public int maxArea(int[] height) {
    if (height == null || height.length <= 1) return 0;
    int left = 0, right = height.length-1;
    int maxArea = 0;
    while (left < right) {
      int area = Math.min(height[left], height[right]) * (right - left);
      if (area > maxArea) maxArea = area;
      if (height[left] < height[right]) left++;
      else right--;
    }
    return maxArea;
  }

  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> ret = new ArrayList<List<Integer>>();
    Arrays.sort(nums); // nlgn
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i] == nums[i-1]) continue;
      int left = i+1, right = nums.length-1;
      while (left < right) {
        int sum = nums[left] + nums[right];
        if (sum < -nums[i]) left++;
        else if (sum > -nums[i]) right--;
        else {
          ArrayList<Integer> l = new ArrayList<Integer>();
          l.add(nums[i]);
          l.add(nums[left]);
          l.add(nums[right]);
          ret.add(l);
          left++; // make mistake.
          while (left < right && nums[left] == nums[left-1]) left++;
        }
      }
    }
    return ret;
  }

  public int threeSumClosest(int[] nums, int target) {
    if (nums == null || nums.length < 3) return -1;
    Arrays.sort(nums);
    int minDiff = Integer.MAX_VALUE;
    int closestSum = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i] == nums[i-1]) continue;
      int left = i+1, right = nums.length-1;
      while (left < right) {
        int sum = nums[i] + nums[left] + nums[right];
        if (sum == target) return target;
        else if (sum < target) {
          if (target - sum < minDiff) {
            minDiff = target - sum;
            closestSum = sum;
          }
          left++;
        } else {
          if (sum - target < minDiff) {
            minDiff = sum - target;
            closestSum = sum;
          }
          right--;
        }
      }
    }
    return closestSum;
  }

  public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> ret = new ArrayList<List<Integer>>();
    if (nums == null || nums.length < 4) return ret;
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i] == nums[i-1]) continue;
      for (int j = i+1; j < nums.length; j++) {
        if (j > i+1 && nums[j] == nums[j-1]) continue;  // make mistake here
        int left = j+1, right = nums.length-1;
        while (left < right) {
          int sum = nums[i] + nums[j] + nums[left] + nums[right];
          if (sum < target) left++;
          else if (sum > target) right--;
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

  public int threeSumSmaller(int[] num, int target) {
    if (num == null || num.length < 3) return 0;
    Arrays.sort(num);
    int total = 0;
    for (int i = 0; i < num.length; i++) {
      int left = i + 1, right = num.length-1;
      while (left < right) {
        int sum = num[i] + num[left] + num[right];
        if (sum < target) {
          while (right > left) {
            total++;
            right--;
          }
        } else {
          right--;
        }
      }
    }
    return total;
  }

  public boolean isStrobogrammatic(String num) {
    if (num == null || num.length() == 0) return false;
    int left = 0, right = num.length() - 1;
    while (left <= right) {
      char leftc = num.charAt(left);
      char rightc = num.charAt(right);
      if ((leftc == rightc && (leftc == '0' || leftc == '1' || leftc == '8'))||
          (leftc == '6' && rightc == '9') ||
          (leftc == '9' && rightc == '6')) {
        left++;
        right--;
      } else {
        return false;
      }
    }
    return true;
  }


  public static void main(String[] args) {
    TwoPointers test = new TwoPointers();
    int[] a =  {-2, 0, 1, 3};
    System.out.println(test.threeSumSmaller(a, 4));
  }
}
