package qiuchi.chen.basic;

import java.util.Arrays;
import java.util.Comparator;

public class AboutArray {
    public static void main(String[] args) {
        int[] d1Arr = new int[]{1, 2};
        int[][] d2Arr = new int[][]{{12, 12}, {12, 12}};
        int[] anotherD1Arr = Arrays.copyOf(d1Arr, d1Arr.length * 2);
        //此方法常用来动态增长数组长度
        int[][] arrOfArr = new int[2][];
        arrOfArr[0] = new int[1];
        arrOfArr[1] = new int[2];//jagged array
        for (int[] each1DArr : arrOfArr)
            for (int eachValue : each1DArr) {
                System.out.println(eachValue);
            }


        Arrays.sort(d2Arr, new Comparator<int[]>() {
            @Override//自定义比较器排序，匿名类继承
            public int compare(int[] o1, int[] o2) {
                return 0;
            }
        });
    }
}
