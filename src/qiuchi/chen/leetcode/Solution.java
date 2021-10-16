package qiuchi.chen.leetcode;

import org.apache.derby.shared.common.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
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
        long absDividend, absDivisor;
        absDividend = Math.abs(dividend);
        absDivisor = Math.abs(divisor);
        if (dividend == Integer.MIN_VALUE) {
            absDividend = Integer.MAX_VALUE;
            ++absDividend;
        }
        if (divisor == Integer.MIN_VALUE) {
            absDivisor = Integer.MAX_VALUE;
            ++absDivisor;
        }
        long result = 0;
        do {
            absDividend -= absDivisor;
            if (absDividend >= 0) {
                ++result;
            } else break;
        } while (true);
        if (result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (-result < Integer.MIN_VALUE) return Integer.MIN_VALUE;

        int res = (int) result;

        if (dividend < 0 && divisor > 0) return -res;
        else if (dividend > 0 && divisor > 0) return res;
        else if (dividend < 0 && divisor < 0) return res;
        else return -res;
    }

    public void nextPermutation(int[] nums) {
        for (int curHeadIndex = nums.length - 2; curHeadIndex >= 0; --curHeadIndex) {
            int curHeadValue = nums[curHeadIndex], minValueNextToHead = Integer.MAX_VALUE, minNextToHeadIndex = nums.length - 1;
            for (int j = nums.length - 1; j > curHeadIndex; --j) {
                int curValue = nums[j];
                if (curValue > curHeadValue && curValue < minValueNextToHead) {
                    minValueNextToHead = curValue;
                    minNextToHeadIndex = j;
                }
            }
            if (minValueNextToHead != Integer.MAX_VALUE) {
                int temp = nums[curHeadIndex];
                nums[curHeadIndex] = nums[minNextToHeadIndex];
                nums[minNextToHeadIndex] = temp;
                Arrays.sort(nums, curHeadIndex + 1, nums.length);
                return;
            }
        }
        Arrays.sort(nums);
    }

    public int search(int[] nums, int target) {
        //升序排列的整数数组 nums 在预先未知的某个点上进行了旋转
        //（例如， [0,1,2,4,5,6,7] 经旋转后可能变为[4,5,6,7,0,1,2] ）。
        //请你在数组中搜索target ，如果数组中存在这个目标值，则返回它的索引，否则返回-1。
        int lLimit = 0, rLimit = nums.length - 1, center = (rLimit + lLimit) / 2, lastCentreValue = nums[center];
        while (nums[center] != target) {
            if (rLimit - lLimit < 3)
                break;
            if (nums[center] > nums[rLimit]) {
                if (nums[center] < target) {
                    lLimit = center;
                } else {
                    if (nums[lLimit] < target) rLimit = center;
                    else lLimit = center;
                }
            } else {
                if (nums[center] < target) {
                    if (nums[rLimit] > target) lLimit = center;
                    else rLimit = center;
                } else rLimit = center;
            }
            center = (lLimit + rLimit) / 2;
        }
        for (int i = lLimit; i <= rLimit; i++) {
            if (nums[i] == target)
                return i;
        }
        return -1;
    }

    public int[] searchRange(int[] nums, int target) {
        //给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
        //如果数组中不存在目标值 target，返回[-1, -1]。
        if (nums.length == 0) return new int[]{-1, -1};
        int lLimit = 0, rLimit = nums.length - 1, center = (rLimit + lLimit) / 2, lastCentreValue = nums[center];
        while (nums[center] != target) {
            if (rLimit - lLimit < 3)
                break;

            if (nums[center] < target)
                lLimit = center;
            else rLimit = center;

            center = (lLimit + rLimit) / 2;
        }
        center = Integer.MAX_VALUE;
        for (int i = lLimit; i <= rLimit; i++) {
            if (nums[i] == target)
                center = i;
        }
        if (center == Integer.MAX_VALUE) return new int[]{-1, -1};

        int lSpan = 0, rSpan = 0;
        while (center - lSpan - 1 != -1) {
            if (nums[center - lSpan - 1] == target) {
                ++lSpan;
            } else break;
        }
        if (center != nums.length - 1)
            while (center + rSpan + 1 != nums.length) {
                if (nums[center + rSpan + 1] == target) {
                    ++rSpan;
                } else break;
            }
        return new int[]{center - lSpan, center + rSpan};
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //给定一个无重复元素的数组candidates和一个目标数target，
        //找出candidates中所有可以使数字和为target的组合。
        //candidates中的数字可以无限制重复被选取。
        HashSet<String> noRepeatCheck = new HashSet<>();
        HashSet<String> singleCheck = new HashSet<>();
        List<List<Integer>> result = new ArrayList<>();
        class RecurOp {
            void run(int[] candi, int target, Integer[] curResult) {
                for (int candidate : candidates) {
                    if (target - candidate > 0) {
                        Integer[] passOn = Arrays.copyOf(curResult, curResult.length + 1);
                        passOn[curResult.length] = candidate;

                        Arrays.sort(passOn);
                        if (noRepeatCheck.add(Arrays.toString(passOn)))
                            run(candidates, target - candidate, passOn);
                    }
                    if (target - candidate == 0) {
                        Integer[] resultArr = Arrays.copyOf(curResult, curResult.length + 1);
                        resultArr[curResult.length] = candidate;
                        Arrays.sort(resultArr);
                        if (singleCheck.add(Arrays.toString(resultArr)))
                            result.add(Arrays.asList(resultArr));
                    }
                }
            }
        }
        RecurOp op = new RecurOp();
        op.run(candidates, target, new Integer[0]);
        return result;
    }

    public boolean isPowerOfTwo(int n) {
        double a = Math.log(n) / Math.log(2);
        return (a - (long) a) == 0;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinationSum(new int[]{2, 3, 6, 7}, 7));
    }
}

class CombinationSum2 {

//    HashSet<int[]> result = new HashSet<>();
//
//    HashSet<int[]> current = new HashSet<>();
//
//    HashMap<int[], int[]> currentMap = new HashMap<>();
//
//    int target;
//
//    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
//
//        int[] sortedDistinct = Arrays.stream(candidates).distinct().sorted().toArray();
//
//        for (int each :
//                sortedDistinct) {
//
//        }
//
//        return null;
//    }
//
//    private void recur() {
//
//        boolean conti = false;
//
//        Set<int[]> old = currentMap.keySet();
//
//
//        for (int[] each : old) {
//
//            int preSum = Arrays.stream(each).sum();
//
//            int[] remainStartPoint = Arrays.stream(currentMap.get(each)).distinct().toArray();
//
//            for (int startInt : remainStartPoint) {
//
//                int curSum = preSum + startInt;
//
//                if (curSum < target) {
//                    conti = true;
//                }
//                if (curSum == target){
//                    curResult=
//                    result.add(Arrays.copyOf(each, old.size()+1))
//                }
//
//            }
//
//        }
//    }
}

class MyPow {
    public double myPow(double x, int n) {
        return Math.pow(x, n);
    }
}

class SolveNQueens {

    public static void main(String[] args) {
        SolveNQueens solveNQueens = new SolveNQueens();
        solveNQueens.solveNQueens(4);
        System.out.println();
    }

    List<List<int[]>> result = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {

        ss(0, n, 0, new ArrayList<>());

        return null;

    }

    private void ss(int available, int size, int curCol, List<int[]> prev) {

        for (int curRow = 0; curRow < size; curRow++) {

            int test = generatePointIndicator(size, curRow, curCol) & available;

            if (test != 0) {
                continue;
            }

            List<int[]> newPrev = new ArrayList<>(prev);

            newPrev.add(new int[]{curRow, curCol});

            if (curCol == size - 1) {
                result.add(newPrev);
            } else {
                ss(available | generateOccupationBitIndicator(size, curRow, curCol), size, curCol + 1, newPrev);
            }
        }
    }

    private int generatePointIndicator(int size, int row, int col) {
        return 1 << (row * size + col);
    }

    private int generateOccupationBitIndicator(int size, int row, int col) {
        HashSet<int[]> markAsUnavailablePoint = new HashSet<>();

        for (int curRow = 0; curRow < size; curRow++) {
            if (curRow == row) continue;
            markAsUnavailablePoint.add(new int[]{col, curRow});
        }
        for (int curCol = 0; curCol < size; curCol++) {
            if (curCol == col) continue;
            markAsUnavailablePoint.add(new int[]{curCol, row});
        }

        for (int step = 1; row + step <= size - 1 && col + step <= size - 1; step++) {
            markAsUnavailablePoint.add(new int[]{row + step, col + step});
        }
        for (int step = -1; row + step >= 0 && col + step >= 0; step--) {
            markAsUnavailablePoint.add(new int[]{row + step, col + step});
        }


        for (int step = 1; row + step <= size - 1 && col - step >= 0; step++) {
            markAsUnavailablePoint.add(new int[]{row + step, col - step});
        }
        for (int step = -1; row + step >= 0 && col - step <= size - 1; step--) {
            markAsUnavailablePoint.add(new int[]{row + step, col - step});
        }

        int unavailable = 0;

        for (int[] eachPoint : markAsUnavailablePoint) {
            int modifier = 1;
            modifier <<= (eachPoint[0] * size + eachPoint[1]);
            unavailable |= modifier;
        }
        return unavailable;
    }
}

class CountPoints {

    public int[] countPoints(int[][] points, int[][] queries) {

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            for (int[] point : points) {
                if (isInVicinity(point, queries[i][2], queries[i])) {
                    result[i]++;
                }
            }
        }

        return result;
    }


    private boolean isInVicinity(int[] targetPoint, int radius, int[] centralPoint) {

        if (Math.abs(centralPoint[0] - targetPoint[0]) > radius || Math.abs(centralPoint[1] - targetPoint[1]) > radius)
            return false;

        return Math.pow(targetPoint[0] - centralPoint[0], 2) + (Math.pow(targetPoint[1] - centralPoint[1], 2)) <= radius * radius;
    }
}

class MinPartitions {

    public static void main(String[] args) {
        System.out.println(minPartitions("32"));
    }

    public static int minPartitions(String n) {

        char largest = 48;

        for (int i = 0; i < n.length(); i++) {

            char a = n.charAt(i);

            if (a > largest)
                largest = a;
        }
        return largest - 48;
    }
}

class LevelOrder {

    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> levelOrder(TreeNode root) {

        ArrayList<TreeNode> start = new ArrayList<>();
        start.add(root);

        if (root == null)
            return new ArrayList<>();

        recur(start);
        return result;
    }

    private void recur(List<TreeNode> curLevel) {

        if (curLevel.size() == 0)
            return;

        List<TreeNode> newLevel = new ArrayList<>();
        List<Integer> newResult = new ArrayList<>();


        for (TreeNode eachNode : curLevel) {

            newResult.add(eachNode.val);

            if (eachNode.left != null)
                newLevel.add(eachNode.left);
            if (eachNode.right != null)
                newLevel.add(eachNode.right);
        }
        result.add(newResult);
        recur(newLevel);
    }
}

/**
 * 给你两个没有重复元素的数组nums1和nums2，其中nums1是nums2的子集。
 * 请你找出nums1中每个元素在nums2中的下一个比其大的值。
 * nums1中数字x的下一个更大元素是指x在nums2中对应位置的右边的第一个比x大的元素。如果不存在，对应位置输出-1。
 **/
class NEXT_GREATER_ELEMENT {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums2.length; ++i) {
            map.put(nums2[i], i);
        }

        for (int i = 0; i < nums1.length; ++i) {

            if (map.get(nums1[i]) == null) {
                nums1[i] = -1;
                continue;
            }

            int startIndex = map.get(nums1[i]) + 1;

            while (startIndex < nums2.length) {

                if (nums2[startIndex] > nums1[i]) {
                    nums1[i] = nums2[startIndex];
                    break;
                }

                ++startIndex;
            }

            if (startIndex == nums2.length) nums1[i] = -1;
        }

        return nums1;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * <br>
 * Solution obj = new Solution(rects);
 * <br>
 * int[] param_1 = obj.pick();
 * <br>
 * 给定一个非重叠轴对齐矩形的列表 rects，写一个函数 pick 随机均匀地选取矩形覆盖的空间中的整数点。
 * <br>
 * 提示：
 * <br>
 * 整数点是具有整数坐标的点。
 * 矩形周边上的点包含在矩形覆盖的空间中。
 * 第 i 个矩形 rects [i] = [x1，y1，x2，y2]，其中[x1，y1] 是左下角的整数坐标，[x2，y2] 是右上角的整数坐标。
 * 每个矩形的长度和宽度不超过 2000。
 * 1 <= rects.length<= 100
 * pick 以整数坐标数组[p_x, p_y]的形式返回一个点。
 * pick 最多被调用10000次。
 */
class RANDOM_POINT_IN_UN_OVERLAP_RECTANGLE {

    public RANDOM_POINT_IN_UN_OVERLAP_RECTANGLE(int[][] rects) {

    }

    public int[] pick() {
        return null;
    }
}

/**
 * 给定一个含有 M x N 个元素的矩阵（M 行，N 列），请以对角线遍历的顺序返回这个矩阵中的所有元素，对角线遍历如下图所示。
 */
class DIAGONAL_ITERATION {

    public static void main(String[] args) {
        new DIAGONAL_ITERATION().findDiagonalOrder(new int[][]{{2, 5}, {8, 4}, {0, -1}});
    }

    public int[] findDiagonalOrder(int[][] mat) {

        int rowCount = mat.length;
        int colCount = mat[0].length;

        int resultIndex = 0;

        int[] result = new int[rowCount * colCount];

        if (mat.length == 1) return mat[0];

        if (mat[0].length == 1) {
            for (int[] eachArr : mat) {
                result[resultIndex] = eachArr[0];
                resultIndex++;
            }
            return result;
        }

        int horizontal = 1;
        int startIndex = 0;


        int maxRowIndex = rowCount - 1;
        int maxColIndex = colCount - 1;

        while (resultIndex < result.length) {

            if (horizontal == 1) {

                int curRowIndex;
                int curColIndex;

                if (startIndex < rowCount) {

                    curRowIndex = startIndex;
                    curColIndex = 0;

                } else {

                    curRowIndex = maxRowIndex;
                    curColIndex = startIndex - maxRowIndex;

                }

                while (curRowIndex >= 0 && curColIndex < colCount) {

                    result[resultIndex] = mat[curRowIndex][curColIndex];

                    curRowIndex--;
                    curColIndex++;
                    resultIndex++;
                }

            } else {

                int curRowIndex;
                int curColIndex;

                if (startIndex < colCount) {

                    curRowIndex = 0;
                    curColIndex = startIndex;

                } else {

                    curRowIndex = startIndex - maxColIndex;
                    curColIndex = maxColIndex;

                }

                while (curColIndex >= 0 && curRowIndex < rowCount) {

                    result[resultIndex] = mat[curRowIndex][curColIndex];

                    curRowIndex++;
                    curColIndex--;
                    resultIndex++;
                }
            }

            startIndex++;
            horizontal *= -1;
        }

        return result;
    }
}

/**
 * 三个不同的线程 A、B、C 将会共用一个Foo实例。
 * <br>
 * 一个将会调用 first() 方法
 * 一个将会调用second() 方法
 * 还有一个将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 */
class Foo {

    public Foo() {

    }

    private volatile int seq = 0;

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();

        seq++;
    }

    public void second(Runnable printSecond) throws InterruptedException {

        while (seq != 1) {
            Thread.onSpinWait();
        }

        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();

        seq++;
    }

    public void third(Runnable printThird) throws InterruptedException {

        while (seq != 2) {
            Thread.onSpinWait();
        }

        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}

/**
 * 两个不同的线程将会共用一个 FooBar实例。其中一个线程将会调用foo()方法，另一个线程将会调用bar()方法。
 * <br>
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 */
class FooBar {

    private int n;

    private final AtomicBoolean isFoo = new AtomicBoolean(true);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; ) {

            if (!isFoo.get()) {

                synchronized (isFoo) {
                    isFoo.wait();
                }

            } else {
                printFoo.run();
                isFoo.set(false);
                synchronized (isFoo) {
                    isFoo.notify();
                }
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; ) {

            if (isFoo.get()) {

                synchronized (isFoo) {
                    isFoo.wait();
                }

            } else {

                printBar.run();
                isFoo.set(true);
                synchronized (isFoo) {
                    isFoo.notify();
                }
            }
        }
    }
}

/**
 * 编写一种方法，对字符串数组进行排序，将所有变位词组合在一起。变位词是指字母相同，但排列不同的字符串。
 */
class GROUP_ANAGRAMS {
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> id_strLst_map = new HashMap<>();

        for (int i = strs.length - 1; i > -1; i--) {

            char[] charArr = strs[i].toCharArray();
            Arrays.sort(charArr);
            String id = new String(charArr);

            if (id_strLst_map.containsKey(id)) {
                id_strLst_map.get(id).add(strs[i]);
            } else {
                id_strLst_map.put(id, new ArrayList<>(Collections.singleton(strs[i])));
            }
        }

        return new ArrayList<>(id_strLst_map.values());
    }
}

/**
 * 给你两个整数数组arr1 和 arr2，返回使arr1严格递增所需要的最小「操作」数（可能为 0）。
 * <br>
 * 每一步「操作」中，你可以分别从 arr1 和 arr2 中各选出一个索引，分别为i 和j，0 <=i < arr1.length和0 <= j < arr2.length，然后进行赋值运算arr1[i] = arr2[j]。
 * <br>
 * 如果无法让arr1严格递增，请返回-1。
 */
class MAKE_ARRAY_INCREASING {
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {

        int[] wholeArr = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, wholeArr, arr1.length, arr2.length);
        Arrays.sort(wholeArr);

        List<int[]> formidable = removeOne(wholeArr, arr1.length);

        formidable = formidable.stream().filter(each -> valid(each, arr1.length)).collect(Collectors.toList());

        int minStep = Integer.MAX_VALUE;

        if (formidable.size() == 0) return -1;

        for (int[] each : formidable) {
            int step = stepCount(arr1, each);
            if (step < minStep) minStep = step;
        }

        return minStep;

    }

    boolean valid(int[] target, int targetLength) {

        if (target.length != targetLength)
            return false;

        int last = target[0];
        for (int i = 1; i < target.length; i++) {
            if (last == target[i]) return false;
        }
        return true;
    }

    int stepCount(int[] src, int[] target) {

        int result = 0;

        for (int i = 0; i < src.length; i++) {
            if (src[i] != target[i]) result++;
        }

        return result;
    }

    List<int[]> removeOne(int[] formidable, int minLength) {

        List<int[]> result = new ArrayList<>();

        result.add(formidable);

        if (formidable.length == minLength) {
            return result;
        }

        for (int rmIdx = 0; rmIdx < formidable.length; rmIdx++) {
            result.addAll(removeOne(rmAt(formidable, rmIdx), minLength));
        }

        return result;
    }

    int[] rmAt(int[] target, int targetIdx) {

        List<Integer> resLst = Arrays.stream(target).boxed().collect(Collectors.toList());

        resLst.remove(targetIdx);

        return resLst.stream().mapToInt(i -> i).toArray();
    }
}