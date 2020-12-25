package qiuchi.chen.exception;

class AboutAssert {
    public static void main(String[] args) {
        int x = -1;
        assert x < 0 : "!";
        assert x > 0 : "!";
        System.out.println("Done");
        //<!>assert要命令行中java -enableassertions才能用
    }
}
