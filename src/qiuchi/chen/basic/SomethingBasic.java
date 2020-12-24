package qiuchi.chen.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Scanner;

public class SomethingBasic {
    static void SharedString() {
        //<!>注意下String pool这个概念
        String a = "123", b = "123";
        System.out.println(a == b);
        //<?>返回true，自动指向已有string？
        System.out.println(b == "123");
    }

    static void TheStringBuilderClass() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(1).append(2).append("asd");
        System.out.println(stringBuilder.toString());
    }

    static void InputAndOutput() {
        //Scanner scanner = new Scanner(System.in);
        //while (scanner.hasNext())
        //    System.out.println(scanner.next());
        //scanner.nextDouble();
        //Console console = System.console();
        //console.readLine("123123");
        //console.readPassword("123123");
        //<!>只有命令行启动才有用，否则返回空指针
        try {
            //<!>Scanner的String构造参数时source，不是path，给啥就读啥
            Scanner in = new Scanner(Paths.get("C:\\Users\\name\\IdeaProjects\\LearningJava\\src\\qiuchi\\chen\\innerclass\\AnonymousInnerClass.java"));
            PrintWriter writer = new PrintWriter("C:\\Users\\name\\Desktop\\test.txt");
            while (in.hasNextLine()) {
                writer.println(in.nextLine());
            }
            System.out.println("Done");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void AboutFormatting() {
        class cls implements Formattable {

            @Override
            public void formatTo(Formatter formatter, int flags, int width, int precision) {
                //<?>这个咋用
            }
        }
        System.out.printf("%8.2f\n", 100.0 / 3);
        System.out.printf("%1$s %2$tB %2$te, %2$tY\n", "Due date:", new java.util.Date());
        //<!>  %{n}${type}可以指对应位置参数
    }

    static void AboutBigNumber() {
        BigInteger bigInteger = BigInteger.valueOf(10);
        bigInteger = bigInteger.add(bigInteger);
        bigInteger.divide(BigInteger.TWO);
        System.out.println(bigInteger.longValue());
    }

    static void AboutCommandLineParameter(String[] args) {
        for (String eachString : args) {
            System.out.println(eachString);
        }
    }

    static void AboutClassComment() {
        /**
         *<code>ClassA</code>
         */
        class ClassA {
            /**
             *
             */
            public int intMem;

            /**
             * @tagName
             * @param p
             * @return a
             * @throws Exception e
             */
            public int MethodReturnsIntValue(int p) throws Exception {
                return 1;
            }
        }
    }

    public enum Size {
        SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");
        private String abbreviation;

        //<->枚举型的构造器是干什么用的：可以有一些功能和数据放在里面
        //构造器的参数对应（）内的东西
        private Size(String abbreviation) {
            this.abbreviation = abbreviation;
            System.out.println("Constructor Called");
        }
    }

    private void About_Cloneable() {
        class ClassCanClone implements Cloneable {
            //Cloneable不规定方法，只在instanceof时起作用
            //此类成为:tagging interface
            @Override
            protected Object clone() throws CloneNotSupportedException {
                //但仍需要重写继承自Object的clone方法来符合语义
                return super.clone();
            }

        }
    }


    private interface ITestInterface {
        final int someConstant = 0;

        void doMethod();
    }

    private static void UseOf_InstanceOf() {

        class AClass implements ITestInterface {

            //接口内能放常量，但不推荐
            @Override
            public void doMethod() {

            }
        }
        AClass instance = new AClass();
        int a = instance.someConstant;
        System.out.println(instance instanceof ITestInterface);
        //<!>注意在对象为null时候instanceof一定返回false
    }

    /**
     * <strong>Description</strong>
     *
     * @param args about args:...
     *             If your comments contain links to other files such as images (for example,
     *             diagrams or images of user interface components), place those files into a
     *             subdirectory of the directory containing the source file, named doc-files. The
     *             javadoc utility will copy the doc-files directories and their contents from the
     *             source directory to the documentation directory. You need to use the docfiles directory in your link,
     *             for example <img src="doc-files/uml.png"alt="UML diagram"/>.
     */
    public static void main(String[] args) {
        UseOf_InstanceOf();
    }
}
