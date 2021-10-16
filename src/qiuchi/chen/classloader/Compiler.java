package qiuchi.chen.classloader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Compiler {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        FileOutputStream outputStream = new FileOutputStream("src/Test1.java");
        outputStream.write(("import java.util.Arrays;\n" +
                "\n" +
                "public class Test {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(Arrays.toString(args));\n" +
                "    }\n" +
                "    public void aaa(){\n" +
                "        System.out.println(\"aaa\");\n" +
                "    }\n" +
                "}\n").getBytes(StandardCharsets.UTF_8));
        outputStream.close();

        int result = compiler.run(null, null, null, "src/Test.java");
        Class<?> a = Class.forName("Test");
        a.getMethod("aaa").invoke(a.getConstructor().newInstance());
    }
}
