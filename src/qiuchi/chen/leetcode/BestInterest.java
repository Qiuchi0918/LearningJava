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

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) return new ArrayList<>();
        int[] digitCodePointArr = new int[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            digitCodePointArr[i] = digits.codePointAt(i) - 50;//0-7
        }
        final ArrayList<String> digitCodePointArrLst = new ArrayList<>();
        class Operator {
            final int[] cpArr = digitCodePointArr;

            void op(String curString, int curPos) {
                if (curPos == cpArr.length) {
                    digitCodePointArrLst.add(curString);
                } else {
                    if (cpArr[curPos] < 5) {
                        for (int i = 0; i < 3; i++) {
                            int curCpValue = cpArr[curPos] * 3 + i + 97;
                            op(curString + Character.toString(curCpValue), curPos + 1);
                        }
                    } else if (cpArr[curPos] == 5) {
                        for (int i = 0; i < 4; i++) {
                            int curCpValue = cpArr[curPos] * 3 + i + 97;
                            op(curString + Character.toString(curCpValue), curPos + 1);
                        }
                    } else if (cpArr[curPos] == 6) {
                        for (int i = 0; i < 3; i++) {
                            int curCpValue = cpArr[curPos] * 3 + 1 + i + 97;
                            op(curString + Character.toString(curCpValue), curPos + 1);
                        }
                    } else {
                        for (int i = 0; i < 4; i++) {
                            int curCpValue = cpArr[curPos] * 3 + 1 + i + 97;
                            op(curString + Character.toString(curCpValue), curPos + 1);
                        }
                    }
                }
            }
        }
        Operator oper = new Operator();
        oper.op("", 0);
        return digitCodePointArrLst;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        //给定一个包含n 个整数的数组nums和一个目标值target，
        //判断nums中是否存在四个元素 a，b，c和 d，使得a + b + c + d的值与target相等？找出所有满足条件且不重复的四元组。
        if (nums.length < 4) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        HashSet<String> reachedBase = new HashSet<>();
        HashSet<String> reachedCombination = new HashSet<>();
        for (int baseOneIndex = 0; baseOneIndex < nums.length - 1; baseOneIndex++) {
            for (int baseTwoIndex = baseOneIndex + 1; baseTwoIndex < nums.length; baseTwoIndex++) {
                int curTarget = target - nums[baseOneIndex] - nums[baseTwoIndex];
                int[] curBase = new int[]{nums[baseOneIndex], nums[baseTwoIndex]};
                Arrays.sort(curBase);
                String curBaseName = Arrays.toString(curBase);
                if (reachedBase.contains(curBaseName)) continue;

                reachedBase.add(curBaseName);
                int lPtr = 0, rPtr = nums.length - 1;
                while (lPtr != rPtr) {
                    if (lPtr == baseOneIndex || lPtr == baseTwoIndex) {
                        lPtr++;
                        continue;
                    }
                    if (rPtr == baseTwoIndex || rPtr == baseOneIndex) {
                        rPtr--;
                        continue;
                    }

                    int curSum = nums[rPtr] + nums[lPtr];
                    int curDifference = curSum - curTarget;
                    if (curDifference == 0) {
                        int a = nums[baseOneIndex], b = nums[baseTwoIndex], c = nums[lPtr], d = nums[rPtr];
                        Integer[] curResultArr = new Integer[]{nums[baseOneIndex], nums[baseTwoIndex], nums[lPtr], nums[rPtr]};
                        Arrays.sort(curResultArr);
                        String curResultName = Arrays.toString(curResultArr);
                        if (!reachedCombination.contains(curResultName)) {
                            reachedCombination.add(curResultName);
                            result.add(new ArrayList<>(Arrays.asList(curResultArr)));
                        }
                        lPtr++;
                    } else if (curDifference < 0) lPtr++;
                    else rPtr--;
                }
            }
        }
        return result;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        //给定一个链表，删除链表的倒数第n个节点，并且返回链表的头结点。
        if (head.next == null) {
            return null;
        }
        ListNode curNode = head;
        Deque<ListNode> deque = new LinkedList<>();
        while (curNode != null) {
            deque.addLast(curNode);
            if (deque.size() == n + 2) deque.removeFirst();
            curNode = curNode.next;
        }
        Iterator<ListNode> iter;
        if (deque.size() == n) {
            iter = deque.iterator();
            iter.next();
            return iter.next();
        } else {
            iter = deque.iterator();
            ListNode lConnect = iter.next();
            iter.next();
            if (iter.hasNext()) {
                lConnect.next = iter.next();
                return head;
            } else {
                lConnect.next = null;
                return head;
            }
        }
    }

    public boolean isValid(String s) {
        //给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

        Stack<Character> characterStack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char curChar = s.charAt(i);
            if (curChar == '(' || curChar == '{' || curChar == '[') {
                characterStack.push(curChar);
            } else {
                if (characterStack.empty()) return false;
                char curPeek = characterStack.peek();
                if (curPeek + 2 == curChar || curPeek + 1 == curChar) {
                    characterStack.pop();
                } else return false;
            }
        }

        if (characterStack.empty())
            return true;
        else
            return false;
    }

    public List<String> generateParenthesis(int n) {
        if (n == 0) return new ArrayList<>();
        HashSet<String> singletonCheck = new HashSet<>();
        List<String> result = new ArrayList<>();
        class RecurOp {
            void run(boolean[] avalIndex, int timeRemain, char[] curResult) {
                if (timeRemain == 0) {
                    String resString = String.valueOf(curResult);
                    if (singletonCheck.add(resString)) {
                        result.add(resString);
                    }
                    return;
                }
                for (int i = 0; i < avalIndex.length - 1; i++) {
                    if (avalIndex[i]) continue;
                    for (int j = i + 1; j < avalIndex.length; j++) {
                        if (avalIndex[j]) continue;
                        boolean[] curAvalIndexPassOn = Arrays.copyOf(avalIndex, avalIndex.length);
                        char[] curResPassOn = Arrays.copyOf(curResult, curResult.length);
                        curAvalIndexPassOn[i] = true;
                        curAvalIndexPassOn[j] = true;
                        curResPassOn[i] = '(';
                        curResPassOn[j] = ')';
                        run(curAvalIndexPassOn, timeRemain - 1, curResPassOn);
                    }
                }
            }
        }
        RecurOp op = new RecurOp();
        op.run(new boolean[n * 2], n, new char[n * 2]);
        return result;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        //给你一个链表数组，每个链表都已经按升序排列。
        //请你将所有链表合并到一个升序链表中，返回合并后的链表。

        Queue<ListNode> queue = new ArrayDeque<>();
        while (true) {
            boolean exit = true;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) exit = false;
            }
            if (exit) break;

            int curMinValue = Integer.MAX_VALUE, curMinIndex = 0;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) {
                    if (lists[i].val < curMinValue) {
                        curMinValue = lists[i].val;
                        curMinIndex = i;
                    }
                }
            }
            queue.add(lists[curMinIndex]);
            lists[curMinIndex] = lists[curMinIndex].next;
        }
        ListNode lresult = new ListNode(), curNode = lresult;
        while (!queue.isEmpty()) {
            curNode.next = queue.poll();
            curNode = curNode.next;
        }
        return lresult.next;
    }

    public ListNode swapPairs(ListNode head) {
        ListNode leftToResult = new ListNode();
        ListNode resPtr = leftToResult;
        ListNode curNode = head;
        Stack<ListNode> reverseStack = new Stack<>();
        while (curNode != null) {
            for (int i = 0; i < 2; i++) {
                if (curNode != null) {
                    reverseStack.add(curNode);
                    curNode = curNode.next;
                }
            }
            while (!reverseStack.isEmpty()) {
                resPtr.next = reverseStack.pop();
                resPtr = resPtr.next;
            }
        }
        resPtr.next = null;
        return leftToResult.next;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode leftToResult = new ListNode();
        ListNode resPtr = leftToResult;
        ListNode curNode = head;
        Stack<ListNode> reverseStack = new Stack<>();
        while (curNode != null) {
            boolean stackFull = false;
            for (int i = 0; i < k; i++) {
                if (curNode != null) {
                    if (i == k - 1) stackFull = true;
                    reverseStack.add(curNode);
                    curNode = curNode.next;
                }
            }
            if (stackFull) {
                while (!reverseStack.isEmpty()) {
                    resPtr.next = reverseStack.pop();
                    resPtr = resPtr.next;
                }
            } else {
                Stack<ListNode> defaultStack = new Stack<>();
                while (!reverseStack.isEmpty()) {
                    defaultStack.add(reverseStack.pop());
                }
                while (!defaultStack.isEmpty()) {
                    resPtr.next = defaultStack.pop();
                    resPtr = resPtr.next;
                }
            }
        }
        resPtr.next = null;
        return leftToResult.next;
    }

    public int removeDuplicates(int[] nums) {
        //给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
        //不要使用额外的数组空间，你必须在原地修改输入数组，并在使用 O(1) 额外空间的条件下完成。
        int writePtr = 0, readIndex = 0, lastDigitWrite = Integer.MIN_VALUE;
        while (readIndex != nums.length) {
            if (nums[readIndex] != lastDigitWrite) {
                nums[writePtr] = nums[readIndex];
                ++writePtr;
                lastDigitWrite = nums[readIndex];
            }
            ++readIndex;
        }
        return writePtr;
    }

    public int removeElement(int[] nums, int val) {
        //给你一个数组 nums和一个值 val，你需要 原地 移除所有数值等于val的元素，并返回移除后数组的新长度。
        //不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
        //元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
        int writePtr = 0, readIndex = 0;
        while (readIndex != nums.length) {
            if (nums[readIndex] != val) {
                nums[writePtr] = nums[readIndex];
                ++writePtr;
            }
            ++readIndex;
        }
        return writePtr;
    }

    public int divide(int dividend, int divisor) {
        //给定两个整数，被除数dividend和除数divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
        //返回被除数dividend除以除数divisor得到的商。
        //整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
        if (dividend == 0) return 0;
        int dividendTemp = dividend, divisorTemp = divisor;

        dividendTemp = Math.abs(dividend);
        divisorTemp = Math.abs(divisor);
        int curValue = divisorTemp, count = 0;
        while (dividendTemp > curValue) {
            count++;
            curValue += divisorTemp;
        }
        if (dividend < 0 && divisor > 0) return -count;
        if (dividend > 0 && divisor > 0) return count;
        if (dividend < 0 && divisor < 0) return count;
        if (dividend > 0 && divisor < 0) return -count;
        return 0;
    }

    //public List<Integer> findSubstring(String s, String[] words) {
    //    //给定一个字符串s和一些长度相同的单词words。找出 s 中恰好可以由words 中所有单词串联形成的子串的起始位置。
    //    //注意子串要与words 中的单词完全匹配，中间不能有其他字符，但不需要考虑words中单词串联的顺序。
    //}

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.divide(1, 1);
    }
}
