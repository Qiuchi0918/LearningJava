package qiuchi.chen.innerclass;

interface ISomeInterface {
    public void But();
}

abstract class AbClass {
    private int mem;

    public AbClass(int arg) {
        mem = arg;
    }

    public int He() {
        return 1;
    }
}

class SomeClass {
    public SomeClass(String arg) {

    }
}

public class AnonymousInnerClass {
    public ISomeInterface Get() {
        return new ISomeInterface() {
            @Override
            public void But() {
            }
        };
    }

    public SomeClass Run(String arg) {
        return new SomeClass(arg) {
            //外部传参可以传给基类的构造器，然后再声明内部子匿名类
            @Override
            public String toString() {
                return super.toString();
                //等同于函数内声明类，也就是LocalInnerClass
            }
        };
    }

    public static void main(String[] args) {
        AnonymousInnerClass outer = new AnonymousInnerClass();
        ISomeInterface anoInner = outer.Get();
        //虚类也可以用跟匿名类相似的方法声明并顺便创建实例
        for (int[] i = {0}; i[0] < 5; i[0]++) {
            AbClass a = new AbClass(i[0]) {
                {
                    System.out.println("Born");
                    i[0]++;
                    //<?>为什么必须要final？
                    //数组怎么不要final也行
                }

                @Override
                public int He() {
                    return super.He();
                }
            };
        }
    }
}
