package qiuchi.chen.multithreading;

public class ThreadSubclass extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("Thread:%s,Iteration:%d\n", getName(), i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public ThreadSubclass(String strName) {
        super();
        setName(strName);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            (new ThreadSubclass((Integer.toString(i)))).start();
        }
    }
}
