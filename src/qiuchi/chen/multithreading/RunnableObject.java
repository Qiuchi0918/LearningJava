package qiuchi.chen.multithreading;

public class RunnableObject implements Runnable {
    public void run() {
        for (int i = 0; i < 10; ++i) {
            System.out.println("Iteration:" + i + ",Thread:" + Thread.currentThread().getName());

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    public RunnableObject() {
    }


    public static void main(String[] args) {
        RunnableObject runnableObj = new RunnableObject();
        new Thread(runnableObj);

        for (int i = 0; i < 10; ++i) {
            RunnableObject object = new RunnableObject();
            (new Thread(object)).start();
        }

    }
}
