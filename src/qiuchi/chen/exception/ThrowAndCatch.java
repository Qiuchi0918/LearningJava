package qiuchi.chen.exception;

import java.io.IOException;

public class ThrowAndCatch {
    static void doSomething() throws MyOwnException {
        try {
            //do something
        } finally {
            //catch is not necessarily required
        }
        throw new MyOwnException();
    }

    static void useDoSomething() throws MyOwnException, IOException {
        try (AutoCloseable autoCloseable = new AutoCloseable() {
            @Override
            public void close() throws Exception {

            }
            //<!>try()内可以声明AutoCloseable变量
            //在退出try块时会自动调用它的close()
        }) {
            doSomething();
        } catch (MyOwnException | Exception e) {
            throw new IOException("Message");
        }
    }

    static void callUseDoSomething() {
        try {
            useDoSomething();
        } catch (MyOwnException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {

        }
    }

    public static void main(String[] args) {
        callUseDoSomething();
    }
}
