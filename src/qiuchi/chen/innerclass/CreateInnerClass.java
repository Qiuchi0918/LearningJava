package qiuchi.chen.innerclass;

public class CreateInnerClass {
    public class InnerClass {
    }

    public static class StaticInnerClass {
        public class InnerClassOfStaticInnerClass {
        }
    }

    public static void main(String[] args) {
        CreateInnerClass outer = new CreateInnerClass();
        InnerClass inner = outer.new InnerClass();

        StaticInnerClass staticInner = new StaticInnerClass();
        //内部类static是指它可以不需要外部类的对象便可以创建实例

        StaticInnerClass.InnerClassOfStaticInnerClass innerinner = staticInner.new InnerClassOfStaticInnerClass();
        //可以反复嵌套
    }
}
