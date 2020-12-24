package qiuchi.chen.basic;

public class TheVarargs {
    public static void methodAcceptVarargs(String... args) {
        //其实就是printf那样的
    }

    public static void main(String[] args) {
        String[] strArr = new String[]{"", "", ""};
        methodAcceptVarargs(strArr);
        //数组也可以放进去
    }
}
