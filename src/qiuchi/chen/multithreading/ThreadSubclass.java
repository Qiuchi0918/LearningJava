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
            //Thread.currentThread().isInterrupted();
            //<!>注意应当检查本线程是否在其他线程有被申请停止
        }
    }

    public ThreadSubclass(String strName) {
        super();
        setName(strName);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ThreadSubclass openNewThread = new ThreadSubclass((Integer.toString(i)));
            openNewThread.start();
            try {
                openNewThread.join();
                //join在别的线程调用，可以阻塞别的线程，直到本线程的return
                //如果本线程已被阻塞，会throw InterruptedException
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Main Thread Current Iteration Complete");
        }
    }
}
