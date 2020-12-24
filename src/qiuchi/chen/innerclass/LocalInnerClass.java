package qiuchi.chen.innerclass;


public class LocalInnerClass {
    public static Runnable SomeMethod() {
        class SomeLIC implements Runnable {
            private int intMem;
            /*static不能用在这里？*/ double staticDMem;

            @Override
            public void run() {

            }
            //这个类只能函数内部用
        }
        return new SomeLIC();
    }

    public static void main(String[] args) {
    }
}
