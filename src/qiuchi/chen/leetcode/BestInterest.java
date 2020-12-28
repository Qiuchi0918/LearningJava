package qiuchi.chen.leetcode;

import org.apache.derby.shared.common.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String ans = "";
        for (int l = 0; l < n; ++l) {
            for (int i = 0; i + l < n; ++i) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true;
                } else if (l == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && l + 1 > ans.length()) {
                    ans = s.substring(i, i + l + 1);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.longestPalindrome("cbbd"));
    }
}
