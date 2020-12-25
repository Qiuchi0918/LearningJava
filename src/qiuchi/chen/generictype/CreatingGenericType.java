package qiuchi.chen.generictype;

import java.io.Serializable;

class Pair<T> {
    private T first;
    private T second;
    private String strName;

    Pair(String strPairName) {
        strName = strPairName;
    }

    Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public Pair<T> minMax(T[] array) {
        return new Pair<T>("");
    }

    public static void main(String[] args) {
        Pair<Integer> pair = new Pair<>("Fine");
    }
}

class NormalClass {
    public static <T> T staticNormalClassMethod(T... args) {
        //<^>在普通类里也可使用泛型方法
        return args[0];
    }

    //描述类型是否实现接口时用extends
    //the so called "bounds"
    public <T extends Comparable & Serializable> T NormalClassMethod(T arg) {
        arg.compareTo(arg);
        return arg;
    }

    //<!>泛型这些东西在编译后都只留下bounds，输入时会检查，输出时会cast
    //这本会导致多态失效，但编译器会插入函数，使对函数的调用从Object或基类再变回T
    //从而保证多态的效果

    //<!>In summary, you need to remember these facts about translation of Java generics:
    //  • There are no generics in the virtual machine, only ordinary classes and methods.
    //  • All type parameters are replaced by their bounds.
    //  • Bridge methods are synthesized to preserve polymorphism.
    //  • Casts are inserted as necessary to preserve type safety.
}

