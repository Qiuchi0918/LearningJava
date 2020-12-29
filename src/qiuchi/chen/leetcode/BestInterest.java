package qiuchi.chen.leetcode;

import org.apache.derby.shared.common.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.util.*;

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}


class Solution {
    private int bestProfit = 0;

    private void bestProfitFunc(int remainingTime, int curBalance, int[] prices) {
        bestProfit = Math.max(bestProfit, curBalance);
        if (prices != null && remainingTime > 0) {
            for (int buyPos = 0; buyPos < prices.length - 1; buyPos++) {
                for (int sellPos = buyPos + 1; sellPos < prices.length; sellPos++) {
                    int curProfit = prices[sellPos] - prices[buyPos];
                    if (curProfit > 0)
                        if (sellPos < prices.length - 2) {
                            bestProfitFunc(remainingTime - 1, curProfit + curBalance, Arrays.copyOfRange(prices, sellPos + 1, prices.length));
                        } else {
                            bestProfitFunc(remainingTime - 1, curProfit + curBalance, null);
                        }
                }
            }
        }
    }

    public int maxProfit(int k, int[] prices) {
        bestProfitFunc(k, 0, prices);
        return bestProfit;
    }

    public int[] twoSum(int[] nums, int target) {
        int[] result = null;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.get(target - nums[i]) == null) {
                hashMap.put(nums[i], i);
            } else {
                result = new int[]{hashMap.get(target - nums[i]), i};
                break;
            }
        }
        return result;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode leftToResult = new ListNode();
        ListNode curNode = leftToResult;
        boolean stepIn = false;
        while (l1 != null || l2 != null) {
            int value = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + (stepIn ? 1 : 0);
            curNode.next = new ListNode(value % 10);
            curNode = curNode.next;
            stepIn = value > 9;
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        if (stepIn)
            curNode.next = new ListNode(1);
        return leftToResult.next;
    }

    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> hashMap = new HashMap<>();
        int startIndex = 0, length = 0;
        for (int curIndex = 0; curIndex < s.length(); curIndex++) {
            if (hashMap.containsKey(s.charAt(curIndex)) && hashMap.get(s.charAt(curIndex)) >= startIndex) {
                length = Math.max(length, curIndex - startIndex);
                startIndex = hashMap.get(s.charAt(curIndex)) + 1;
            }
            hashMap.put(s.charAt(curIndex), curIndex);
        }
        length = Math.max(length, s.length() - startIndex);
        return length;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double length = nums1.length + nums2.length;
        int nums1CurIndex = 0, nums2CurIndex = 0;
        int lastDigit = 0;
        while (nums1CurIndex + nums2CurIndex < length / 2) {
            if (nums1CurIndex == nums1.length) {
                lastDigit = nums2[nums2CurIndex];
                nums2CurIndex++;
            } else if (nums2CurIndex == nums2.length) {
                lastDigit = nums1[nums1CurIndex];
                nums1CurIndex++;
            } else if (nums1[nums1CurIndex] < nums2[nums2CurIndex]) {
                lastDigit = nums1[nums1CurIndex];
                nums1CurIndex++;
            } else {
                lastDigit = nums2[nums2CurIndex];
                nums2CurIndex++;
            }
        }
        if (length % 2 == 1) {
            return lastDigit;
        } else {
            if (nums1CurIndex != nums1.length && nums2CurIndex != nums2.length) {
                if (nums1[nums1CurIndex] < nums2[nums2CurIndex]) {
                    return (lastDigit + nums1[nums1CurIndex]) / 2.0;
                } else {
                    return (lastDigit + nums2[nums2CurIndex]) / 2.0;
                }
            } else if (nums1CurIndex == nums1.length) {
                return (lastDigit + nums2[nums2CurIndex]) / 2.0;
            } else {
                return (lastDigit + nums1[nums1CurIndex]) / 2.0;
            }
        }
    }

    public String longestPalindrome(String s) {
        int length = s.length();
        boolean[][] dp = new boolean[length][length];

        for (int i = 0; i < length - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
        }
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }

        for (int j = 2; j < length; j++) {
            for (int i = 0; i < j - 1; i++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1];
            }
        }

        int start = 0, end = 0, longest = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                if (dp[i][j] && j - i + 1 > longest) {
                    longest = j - i + 1;
                    start = i;
                    end = j;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    public int minDistance(String word1, String word2) {
        if (word1.length() == 0) return word2.length();
        if (word2.length() == 0) return word1.length();

        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i < word1.length() + 1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j < word2.length() + 1; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i < word1.length() + 1; i++) {
            for (int j = 1; j < word2.length() + 1; j++) {
                int left = dp[i - 1][j] + 1;
                int down = dp[i][j - 1] + 1;
                int left_down = dp[i - 1][j - 1];
                if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    left_down += 1;
                }
                dp[i][j] = Math.min(left, Math.min(down, left_down));
            }
        }
        return dp[word1.length()][word2.length()];
    }

    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }


        int cycle = numRows * 2 - 2;
        StringBuilder[] stringBuilders = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            stringBuilders[i] = new StringBuilder();
        }
        for (int i = 0; i < s.length(); i++) {
            int posParam = i % cycle;
            if (posParam < numRows) {
                stringBuilders[posParam].append(s.charAt(i));
            } else {
                stringBuilders[cycle - posParam].append(s.charAt(i));
            }
        }
        String result = "";
        for (int i = 0; i < numRows; i++) {
            result += stringBuilders[i].toString();
        }
        return result;
    }

    public int reverse(int x) {
        int sign = 1;
        String num = String.valueOf(x);
        if (num.charAt(0) == '-') {
            sign = -1;
            num = num.substring(1, num.length());
        }
        num = num.transform(s -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = s.length() - 1; i >= 0; i--) {
                stringBuilder.append(s.charAt(i));
            }
            return stringBuilder.toString();
        });
        long lResult = Long.parseLong(num);
        if (Integer.MAX_VALUE < lResult)
            return 0;
        else return sign * Integer.parseInt(num);
    }

    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        String num = String.valueOf(x);
        for (int i = 0; i < num.length() / 2; i++) {
            if (num.charAt(i) != num.charAt(num.length() - i - 1))
                return false;
        }
        return true;
    }

    public int maxArea(int[] height) {
        if (height.length < 2)
            return 0;
        int lPos = 0, rPos = height.length - 1;
        int best = 0;

        do {
            best = Math.max(best, Math.min(height[lPos], height[rPos]) * (rPos - lPos));
            if (height[lPos] > height[rPos])
                --rPos;
            else
                ++lPos;
        } while (lPos != rPos);
        return best;
    }

    public String intToRoman(int num) {
        String[] combination = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] value = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            while (num / value[i] != 0) {
                stringBuilder.append(combination[i]);
                num -= value[i];
            }
        }
        return stringBuilder.toString();
    }

    public int romanToInt(String s) {
        String[] combination = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] value = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int result = 0;
        for (int i = 0; i < 13; i++) {
            int curUnitValue = value[i];
            String curUnit = combination[i];
            int curUnitLength = curUnit.length();
            while (s.length() >= curUnitLength && s.substring(0, curUnitLength).equals(curUnit)) {
                s = s.substring(curUnitLength);
                result += value[i];
            }
        }
        return result;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        HashSet<Integer> numsNoDuplicate = new HashSet<>();
        HashMap<Integer, Integer> intCount = new HashMap<>();

        for (int num : nums) {
            if (intCount.containsKey(num)) {
                intCount.put(num, intCount.get(num) + 1);
            } else {
                intCount.put(num, 1);
            }
            numsNoDuplicate.add(num);
        }
        HashMap<String, Integer[]> result = new HashMap<>();
        for (int seed : numsNoDuplicate) {
            for (Integer second : numsNoDuplicate) {
                if (numsNoDuplicate.contains(-seed - second)) {
                    Integer[] curResult = new Integer[]{seed, second, -seed - second};
                    Arrays.sort(curResult);
                    for (int i = 0; i < 3; i++) {
                        int curIntCount = intCount.get(curResult[i]);
                        int realCount = 0;
                        for (int j = 0; j < 3; j++) {
                            if (curResult[j].equals(curResult[i]))
                                realCount++;
                        }
                        if (realCount <= curIntCount)
                            result.put(Arrays.toString(curResult), curResult);
                    }
                }
            }
        }
        List<List<Integer>> resultList = new ArrayList<>();
        for (Integer[] sets : result.values()) {
            resultList.add(new ArrayList<Integer>(Arrays.asList(sets)));
        }
        return resultList;
    }

    public int threeSumClosest(int[] nums, int target) {
        //给定一个包括n个整数的数组nums和一个目标值target。找出nums中的三个整数，
        //使得它们的和与target最接近。返回这三个数的和。假定每组输入只存在唯一答案。
        Arrays.sort(nums);
        int[] result = new int[2];
        int difference = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; ++i) {
            int curTarget = target - nums[i];

            int lPtr = 0, rPtr = nums.length - 1;
            while (lPtr != rPtr) {
                if (lPtr == i) {
                    lPtr++;
                    continue;
                }
                if (rPtr == i) {
                    rPtr--;
                    continue;
                }

                int curSum = nums[rPtr] + nums[lPtr];
                int curDifference = curSum - curTarget;
                if (Math.abs(curDifference) < Math.abs(difference)) {
                    difference = curDifference;
                }
                if (curDifference == 0) return target + difference;
                else if (curDifference < 0) lPtr++;
                else rPtr--;
            }
        }
        return target + difference;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.threeSumClosest(new int[]{-1, 2, 1, -4}, 1);
    }
}
