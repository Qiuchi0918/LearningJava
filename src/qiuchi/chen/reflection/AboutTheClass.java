package qiuchi.chen.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

interface ITestInterface {
    int as = 0;//<!>接口内的数据成员是自动final的

    private int method() {
        return 1;
    }
}

public class AboutTheClass {
    int intMem;
    double doubleMem;

    void voidMethod() {
    }

    public static void main(String[] args) {
        AboutTheClass atcInstance = new AboutTheClass();
        System.out.println(atcInstance.getClass().getName());
        System.out.println(atcInstance.getClass().getClass().getName());
        try {
            Class correspondingClassInstance = Class.forName("qiuchi.chen.reflection.AboutTheClass");
            System.out.println(correspondingClassInstance.getName());
            //<!>Class是对应类型的Class不是原来的类本身
            //forName返回的是一个“Class对象”，不是“AboutTheClass对象”
            //注意forName()如果类在包内则必须全名指定
            System.out.print("Public Fields:");
            for (Field eachField : correspondingClassInstance.getFields())
                System.out.println(eachField.getName());
            System.out.print("\nDeclared Fields:");
            for (Field eachField : correspondingClassInstance.getDeclaredFields())
                System.out.print(eachField.getName() + ' ');
            ITestInterface testInterfaceImpl = new ITestInterface() {
            };
            System.out.print("\nDeclared Fields Of Interface:");
            for (Field eachField : ITestInterface.class.getDeclaredFields())
                System.out.print(eachField.getName() + ' ');

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
//<!>The virtual machine manages a unique Class object for each type.
// Therefore, you can use the ==
//operator to compare class objects. For example:
//if (e.getClass() == Employee.class) . . .
//Another example of a useful method is one that lets you create an instance of a class on the fly. This
//method is called, naturally enough, newInstance(). For example,
//e.getClass().newInstance();