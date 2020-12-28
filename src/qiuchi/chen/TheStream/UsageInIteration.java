package qiuchi.chen.TheStream;

import java.util.stream.Stream;

class UsageInIteration {
    public static void main(String[] args) {

        System.out.println(//<!>注意下这里面都是lambda表达式，和for的区别
                Stream.iterate(1, integer -> integer <= 20, integer -> integer + 1).count());
    }
}
