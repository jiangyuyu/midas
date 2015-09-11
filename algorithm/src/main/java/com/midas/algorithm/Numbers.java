package com.midas.algorithm;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xli1 on 9/10/15.
 */
public class Numbers {
  private void setup(Map<Integer, String> numToWords) {
    numToWords.put(1, "One");
    numToWords.put(2, "Two");
    numToWords.put(3, "Three");
    numToWords.put(4, "Four");
    numToWords.put(5, "Five");
    numToWords.put(6, "Six");
    numToWords.put(7, "Seven");
    numToWords.put(8, "Eight");
    numToWords.put(9, "Nine");
    numToWords.put(10, "Ten");
    numToWords.put(11, "Eleven");
    numToWords.put(12, "Twelve");
    numToWords.put(13, "Thirteen");
    numToWords.put(14, "Fourteen");
    numToWords.put(15, "Fifteen");
    numToWords.put(16, "Sixteen");
    numToWords.put(17, "Seventeen");
    numToWords.put(18, "Eighteen");
    numToWords.put(19, "Nineteen");
    numToWords.put(20, "Twenty");
    numToWords.put(30, "Thirty");
    numToWords.put(40, "Forty");
    numToWords.put(50, "Fifty");
    numToWords.put(60, "Sixty");
    numToWords.put(70, "Seventy");
    numToWords.put(80, "Eighty");
    numToWords.put(90, "Ninety");
  }
  // where to put space
  public String numberToWords(int num) {
    if (num == 0) return "Zero";
    StringBuffer word = new StringBuffer();
    String[] orders = {"Hundred", "Thousand", "Million", "Billion"};
    Map<Integer, String> numToWordsMap = new HashMap<Integer, String>();
    setup(numToWordsMap);

    int order = 0;
    while (num > 0) {
      int remain = num % 1000;
      if (remain > 0) {  //trick, e.g. 1,000,000
        StringBuffer temp  = new StringBuffer();
        if (remain >= 100) {
          temp.append(numToWordsMap.get(remain/100) + " " + "Hundred");
          remain = remain % 100;
        }
        if (remain != 0) {
          if (temp.length() > 0) temp.append(" ");    // trick
          if (remain > 20) {
            temp.append(numToWordsMap.get(remain/10 * 10));
            if (remain%10 != 0) { // trick
              temp.append(" " + numToWordsMap.get(remain % 10));
            }
          } else {
            temp.append(numToWordsMap.get(remain));
          }
        }
        if (order > 0) {
          temp.append(" " + orders[order] + " ");
        }

        word.insert(0, temp);
      }

      num = num / 1000;
      order++;
    }
    return word.toString().trim();
  }

  public static void main(String[] args) {
    Numbers op = new Numbers();
    System.out.println(op.numberToWords(100000000));
  }
}
