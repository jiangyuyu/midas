package midas.learning.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class math {
  private HashMap<Character, Integer> intMap = new HashMap<Character, Integer>();
  private HashMap<Integer, String> romanMap = new HashMap<Integer, String>();
  public math() {
    intMap.put('I', 1);
    intMap.put('V', 5);
    intMap.put('X', 10);
    intMap.put('L', 50);
    intMap.put('C', 100);
    intMap.put('D', 500);
    intMap.put('M', 1000);

    romanMap.put(1, "I");
    romanMap.put(5,	"V");
    romanMap.put(10, "X");
    romanMap.put(50, "L");
    romanMap.put(100, "C");
    romanMap.put(500, "D");
    romanMap.put(1000, "M");
  }
  public int romanToInteger(String roman) {
    int ret = 0;
    if (roman == null || roman.isEmpty()) return ret;
    int n = roman.length();
    for (int i = 0; i < n; i++) {
      char c = roman.charAt(i);
      if (i + 1 < n && intMap.get(roman.charAt(i+1)) > intMap.get(c)) {
        ret += intMap.get(roman.charAt(i+1)) - intMap.get(c);
        i++;
      } else {
        ret += intMap.get(c);
      }
    }
    return ret;
  }

  public String integerToRoman(int num) {
    if (num < 1 && num > 3999) return null;
    StringBuffer sb = new StringBuffer();
    int[] scale = {1000, 500, 100, 50, 10, 5, 1};
    for (int i = 0; i < scale.length; i = i+2) {
      int n = num/scale[i];
      if (n > 0) {
        if (n == 4) {
          sb.append(romanMap.get(scale[i]) + romanMap.get(scale[i-1]));
        } else if (n == 9) {
          sb.append(romanMap.get(scale[i]) + romanMap.get(scale[i-2]));
        } else {
          int remain = n;
          if (n >= 5) {
            sb.append(romanMap.get(scale[i - 1]));
            remain = n-5;
          }
          for (int j = 0; j < remain; j++) {
            sb.append(romanMap.get(scale[i]));
          }
        }
      }
      n = num % scale[i];
    }
    return sb.toString();
  }

  public int bulbSwitch(int n) {
    int ret = 0;
    while (ret * ret <= n) ret++;
    return ret-1;

    // return (int)Math.sqrt(n);
  }

  public int addDigits(int num) {
    if (num < 0) throw new IllegalArgumentException();
    while (num >= 10) {
      int sum = 0;
      while (num > 0) {
        sum += num%10;
        num = num/10;
      }
      num = sum;
    }
    return num;
  }

  public int addDigitsSmart(int num) {
    if (num == 0) return 0;
    if (num < 0) throw new IllegalArgumentException();
    return (num-1)%9 + 1;
  }

  public int[] plusOne(int[] array) {
    if (array == null || array.length == 0) return null;
    int n = array.length;
    int[] temp = new int[n];
    int carry = 1;
    for (int i = n-1; i >= 0; i--) {
      int sum = array[i] + carry;
      temp[i] = sum%10;
      carry = sum/10;
    }
    if (carry == 0) return temp;
    int[] ret = new int[n+1];
    ret[0] = carry;
    for (int i = 0; i < n; i++) {
      ret[i+1] = temp[i];
    }
    return ret;
  }

  public int reverseInteger(int x) {
    boolean isNeg = false;
    if (x < 0) {
      if (x == Integer.MIN_VALUE) return 0;
      x = -x;
      isNeg = true;
    }
    int ret = 0;
    while (x > 0) {
      int temp = x % 10;
      if ((Integer.MAX_VALUE - x) / 10 < ret) {
        return 0;
      } else {
        ret = ret * 10 + temp;
        x = x / 10;
      }
    }
    if (isNeg) return -ret;
    return ret;
  }

  public boolean isHappyNumber(int n) {
    if (n <= 0) return false;
    HashSet<Integer> set = new HashSet<Integer>();
    while (n > 1) {
      int temp = 0;
      while (n > 0) {
        temp += (n%10) * (n%10);
        n = n/10;
      }
      if (!set.contains(temp)) {
        set.add(temp);
      } else {
        return false;
      }
      n = temp;
    }
    return n == 1;
  }

  public int excelColumnToNumber(String s) {
    if (s == null || s.isEmpty()) return 0;
    int ret = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c < 'A' || c > 'Z') return 0;
      ret = ret * 26 + ((c - 'A') + 1);
    }
    return ret;
  }

  public String numberToExcelColumn(int i) {
    if (i <= 0) return "";
    StringBuilder sb = new StringBuilder();
    while (i > 0) {
      sb.append((char)((i-1)%26 + 'A'));
      i = (i-1)/26;
    }
    return sb.reverse().toString();
  }

  public int trailingZero(int n) {
    if (n <= 0) return 0;
    int total = 0;
    while (n > 0) {
      total += n/5;
      n = n/5;
    }
    return total;
  }

  public boolean isPalindrome(int n) {
    if (n < 0) {
      return false;
    }
    int temp = n;
    int rank = -1;
    while (temp > 0) {
      rank++;
      temp = temp/10;
    }
    rank = (int)Math.pow(10, rank);
    while (n > 0) {
      int head = n/rank;
      int tail = n%10;
      if (head != tail) return false;
      n = (n%rank)/10;
      rank = rank/100;
    }
    return true;
  }

  public boolean isPowerOfThree(int n) {
    return (n > 0 && 1162261467%n == 0);
  }

  public boolean isPowerOfThreeLog(int n) {
    return (n > 0 && ((int)(Math.log(n)/Math.log(3))) - Math.log(n)/Math.log(3) == 0);
  }
  public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
    if(C<E||G<A )
      return (G-E)*(H-F) + (C-A)*(D-B);

    if(D<F || H<B)
      return (G-E)*(H-F) + (C-A)*(D-B);

    int right = Math.min(C,G);
    int left = Math.max(A,E);
    int top = Math.min(H,D);
    int bottom = Math.max(F,B);

    return (G-E)*(H-F) + (C-A)*(D-B) - (right-left)*(top-bottom);
  }

  public String addBinary(String a, String b) {
    if (a == null || b == null) return a == null? b : a;
    if (a.isEmpty() || b.isEmpty()) return a.isEmpty()? b : a;
    int m = a.length(), n = b.length();
    StringBuffer sb = new StringBuffer();
    int carry = 0;
    int i = m-1, j = n-1;
    while (i >= 0 && j >= 0) {
      char ca = a.charAt(i--), cb = b.charAt(j--);
      if (ca == '0' && cb == '0') {
        sb.append(carry);
        carry = 0;
      } else if (ca == '1' && cb == '1') {
        if (carry == 0) {
          sb.append("0");
        } else {
          sb.append("1");
        }
        carry = 1;
      } else {
        if (carry == 0) {
          sb.append("1");
        } else {
          sb.append("0");
          carry = 1;
        }
      }
    }
    if (j >= 0) {
      i = j;
      a = b;
    }
    while (i >= 0) {
      char c = a.charAt(i--);
      if (c == '0') {
        sb.append(carry);
        carry = 0;
      } else {
        if (carry == 0) {
          sb.append(1);
          carry = 0;
        } else {
          sb.append(0);
          carry = 1;
        }
      }
    }
    if (carry == 1) sb.append("1");
    return sb.reverse().toString();
  }

  public boolean isPowerOfTwo(int n) {
    return (n > 0 && (n & (n-1))==0);
    // return (n > 0 && -(Integer.MIN_VAL/2)%n == 0);
  }

  public int myAtoi(String str) {
    if (str == null || str.isEmpty()) return 0;
    int i = 0, n = str.length();
    int ret = 0;
    boolean isNeg = false;
    while (i < n && str.charAt(i) == ' ') i++;
    char c = str.charAt(i);
    if (c == '-' || c == '+') {
      if (c == '-') isNeg = true;
      i++;
    }
//    while (i < n && str.charAt(i) == ' ') i++;
    while (i < n && Character.isDigit(c = str.charAt(i))) {
      int val = c - '0';
      if ((Integer.MAX_VALUE - val)/10 < ret) {
        if (!isNeg) return Integer.MAX_VALUE;
        else return Integer.MIN_VALUE;
      }
      else {
        ret = ret * 10 + val;
      }
      i++;
    }
    if (isNeg) return -ret;
    return ret;
  }

  public int countPrimes(int n) {
    if (n < 2) return 0;
    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);
    for (int i = 2; i * i < n; i++) {
      if (!isPrime[i]) continue;
      for (int j = 2; i * j < n; j++) {
        isPrime[i*j] = false;
      }
    }
    int ret = 0;
    for (int i = 2; i < n; i++) {
      if (isPrime[i]) ret++;
    }
    return ret;
  }

  public int maxRotateFunction(int[] A) {
    if (A == null || A.length == 0) return 0;
    int max = Integer.MIN_VALUE;
    int n = A.length;
    int pre = 0, sum = 0;
    for (int i = 0; i < n; i++) {
      pre += i * A[i];
      sum += A[i];
    }
    max = pre;
    for (int i = 1; i < n; i++) {
      int cur = pre + sum - A[n-i] * n;
      max = max < cur ? cur : max;
      pre = cur;
    }
    return max;
  }

  public int minMoves(int[] arr) {
    if (arr == null || arr.length <= 1) return 0;
    int total = 0;
    int min = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if (min > arr[i]) min = arr[i];
    }
    for (int i = 0; i < arr.length; i++) {
      total += arr[i] - min;
    }
    return total;
  }

  public int minMoves2(int[] nums) {
    if (nums == null || nums.length <= 1) return 0;
    Arrays.sort(nums);
    int n = nums.length;
    int median = nums[n/2];
    int ret = 0;
    for (int i = 0; i < n; i++) {
      if (i == n/2) continue;
      ret += Math.abs(nums[i] - median);
    }
    return ret;
  }

  public String addString(String a, String b) {
    if (a == null || b == null) return a == null ? b : a;
    if (a.length() == 0 || b.length() == 0) return a.length() == 0 ? b : a;
    int m = a.length(), n = b.length();
    StringBuilder sb = new StringBuilder();
    int i = m-1, j = n-1;
    int carry = 0;
    while (i >= 0 && j >= 0) {
      char c1 = a.charAt(i--), c2 = b.charAt(j--);
      int sum = carry + (c1 - '0') + (c2 - '0');
      carry = sum/10;
      sb.append(sum%10);
    }
    if (j >= 0) {
      i = j;
      a = b;
    }
    while (i >= 0) {
      char c = a.charAt(i--);
      int sum = carry + (c - '0');
      carry = sum/10;
      sb.append(sum%10);
    }
    if (carry > 0) sb.append(carry);
    return sb.reverse().toString();
  }

  public boolean isStrobogrammatic(String num) {
    if (num == null || num.isEmpty()) return false;
    int n = num.length();
    int i = 0, j = n-1;
    while (i <= j) {
      char c1 = num.charAt(i++), c2 = num.charAt(j--);
      if (c1 == c2 && (c1 == '1' || c1 == '0' || c1 == '8')) continue;
      if (c1 != c2 && ((c1 == '6' && c2 == '9') || (c1 == '9' && c2 == '6'))) continue;
      return false;
    }
    return true;
  }

  public int nthDigit(int n) {
    int len = 1;
    long count = 9;
    int start = 1;

    while (n > len * count) {
      n -= len * count;
      len += 1;
      count *= 10;
      start *= 10;
    }
    // find the number which the digit comes from
    start += (n - 1) / len;
    String s = Integer.toString(start);
    // find out the position of digit in the number
    return s.charAt((n - 1) % len) - '0';
  }

  public int arrangeCoins(int n) {
    if (n <= 0) return 0;
    int level = 0, count = 1;
    while (n >= count) {
      level++;
      n = n-count;
      count++;
    }
    return level;
  }

  public int arrangCoins(int n) {
    return (int)((-1 + Math.sqrt(1 + 8 * (long)n)) / 2);
  }

  public int bulbSwitcher(int n) {
    int res = 1;
    while (res * res <= n) {
      res++;
    }
    return res-1;
  }

  public int buldSwitcherFast(int n) {
    return (int)(Math.sqrt(n));
  }

  public double pow(double x, int n) {
    if (n == 0) return 1.0;
    double ret = 1.0;
    // consider when n is negative
    boolean isNegRank = false;
    if (n < 0) {
      isNegRank = true;
      if (n == Integer.MIN_VALUE) {
        ret = x;
        n = Integer.MAX_VALUE;
      } else {
        n = -n;
      }
    }

    double base = x;
    while (n > 0) {
      if (n % 2 == 0) {
        x = x*x;
        n = n/2;
      } else {
        ret = ret * x;
        n--;
      }
    }
    if (!isNegRank) return ret;
    return 1.0/ret;
  }

  public int missingNumber(int[] array) {
    if (array == null || array.length == 0) return 0;
    int[] temp = new int[array.length+1];
    Arrays.fill(temp, -1);
    for (int i = 0; i < array.length; i++) {
      int val = array[i];
      temp[val] = val;
    }
    for (int i = 0; i < temp.length; i++) {
      if (temp[i] == -1) return i;
    }
    return -1;
  }

  public int missingNumberNoExtraSpace(int[] array) {
    if (array == null || array.length == 0) return 0;
    int n = array.length;
    for (int i = 0; i < n; i++) {
      if (array[i] == i || array[i] >= n) continue;
      else {
        int temp = array[i];
        array[i] = array[temp];
        array[temp] = temp;
        i--;
      }
    }
    for (int i = 0; i < n; i++) {
      if (i != array[i]) return i;
    }
    return n;
  }

  public int missingNumberMath(int[] array) {
    if (array == null || array.length == 0) return 0;
    int n = array.length;
    int expectSum = (1 + n) * n /2;
    int sum = 0;
    for (int i = 0; i < n; i++) sum += array[i];
    return expectSum - sum;
  }

  public int leastPerfectSquareSumToN(int n) {
    if (n == 0 || n == 1) return n;
    int[] f = new int[n+1];

    Arrays.fill(f, Integer.MAX_VALUE);
    f[0] = 0;
    for (int i = 0; i <= n; i++) {
      for (int j = 1; i + j*j <= n; j++) {
        f[i + j*j] = Math.min(f[i + j * j], 1 + f[i]);
      }
    }
    return f[n];
  }

  public int sqrt(int x) {
    if (x < 0) throw new IllegalArgumentException();
    if (x <= 1) return x;
    int left = 1, right = x;
    while (left <= right) {
      int m = left + (right - left)/2;
      if (Integer.MAX_VALUE / m < m) right = m-1;
      else {
        if (m * m == x) return m;
        else if (m * m < x) left = m+1;
        else right = m-1;
      }
    }
    return right;
  }

  public String multiplyString(String s1, String s2) {
    if (s1 == null || s1.isEmpty()) return s2;
    if (s2 == null || s2.isEmpty()) return s1;
    int m = s1.length(), n = s2.length();
    int[] res = new int[m+n-1];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        res[i+j] += (s1.charAt(i)-'0') * (s2.charAt(j) - '0');
      }
    }
    StringBuffer sb = new StringBuffer();
    int carry = 0;
    for (int i = m+n-2; i >= 0; i--) { // read from the end to front
      int sum = res[i] + carry;
      carry = sum/10;
      sb.append(sum%10);
    }
    if (carry != 0) sb.append(carry);
    return sb.reverse().toString();
  }

  public int numWithUniqueDigits(int n) {
    if (n <= 0) return 1;
    int ret = 0;
    for (int i = 1; i <= n; i++) {
      if (i == 1) ret += 10;
      else {
        int temp = 1;
        for (int j = 1; j <= i; j++) {
          if (j == 1) temp = temp * 9;
          else {
            temp = temp * (10 - j +1);
          }
        }
        ret += temp;
      }
    }
    return ret;
  }

  public String recurDecimal(int numerator, int denumerator) {
    StringBuilder sb = new StringBuilder();
    if (numerator >= denumerator) {
      sb.append(numerator/denumerator);
      numerator = numerator % denumerator;
    } else {
      sb.append("0");
    }
    return null;
  }

  public int getIntegerBreaker(int n) {
    if (n < 2 || n > 58) return 0;
    int[] f = new int[n+1];
    f[1] = 1;
    for (int i = 2; i <= n; i++) {
      for (int j = 1; j <= i/2; j++) {
        f[i] = Math.max(j, f[j]) * Math.max(i-j, f[i-j]);
      }
    }
    return f[n];
  }

  public int getIntegerBreakerDP2(int n) {
    int[] f = new int[n+1];
    f[1] = 1;
    if (n < 2 || n > 58) return 0;
    for (int i = 1; i < n; i++) {
      for (int j = i+1; j < n; j++) {
        if (i + j < n) {
          f[i+j] = Math.max(i, f[i]) * Math.max(j, f[j]);
        }
      }
    }
    return f[n];
  }

  public List<String> findStrobogrammatic(int n) {
    List<String> ret = new LinkedList<String>();
    if (n == 0) return new LinkedList<String>();
    char[] arr = new char[n];
    findStrobogrammaticHelper(arr, 0, n-1, ret);
    return ret;
  }
  private void findStrobogrammaticHelper(char[] arr, int start, int end, List<String> ret) {
    char[] selfSym = {'0', '1', '8'};
    if (start > end) {
      ret.add(new String(arr));
      return;
    } else if (start == end) {
      for (char c : selfSym) {
        arr[start] = c;
        ret.add(new String(arr));
      }
      return;
    }
    for (char c : selfSym) {
      if (start == 0 && c == '0') continue;
      arr[start] = arr[end] = c;
      findStrobogrammaticHelper(arr, start+1, end-1, ret);
    }
    arr[start] = '6'; arr[end] = '9';
    findStrobogrammaticHelper(arr, start+1, end-1, ret);
    arr[start] = '9'; arr[end] = '6';
    findStrobogrammaticHelper(arr, start+1, end-1, ret);
  }
  public int countStrobogrammaticInRange(String low, String high) {
    if (low == null || high == null) return -1;
    int m = low.length(), n = high.length();
    int ret = 0;
    for (int len = m; len <= n; len++) {
      char[] temp = new char[len];
      int[] count = new int[1];
      countStrobogrammatic(temp, 0, len-1, count, low, high);
      ret += count[0];
    }
    return ret;
  }
  public void countStrobogrammatic(char[] arr, int start, int end, int[] count, String low, String high) {
    char[] selfSym = {'0', '1', '8'};
    if (start > end) {
      String result = new String(arr);
      if (inrange(result, low, high))
        count[0]++;
      return;
    } else if (start == end) {
      for (char c : selfSym) {
        arr[start] = c;
        if (inrange(new String(arr), low, high)) {
          count[0]++;
        }
      }
      return;
    }

    for (char c : selfSym) {
      if (start == 0 && c == '0') continue;
      arr[start] = arr[end] = c;
      countStrobogrammatic(arr, start+1, end-1, count, low, high);
    }
    arr[start] = '6'; arr[end] = '9';
    countStrobogrammatic(arr, start+1, end-1, count, low, high);
    arr[start] = '9'; arr[end] = '6';
    countStrobogrammatic(arr, start+1, end-1, count, low, high);
  }
  private boolean inrange(String s, String low, String high) {
    if (s.length() == low.length() && s.compareTo(low) < 0) {
      return false;
    } else if (s.length() == high.length() && s.compareTo(high) > 0) {
      return false;
    }
    return true;
  }

  public boolean isConvex(List<List<Integer>> points) {
    
  }

  public static void main(String[] args) {
    math test = new math();
    int[] arr = {Integer.MIN_VALUE, Integer.MIN_VALUE};
    char[] array = new char[2];
    int[] count = new int[1];
    System.out.println(test.countStrobogrammaticInRange("50", "100"));
  }
}
