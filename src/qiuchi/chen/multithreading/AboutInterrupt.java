package qiuchi.chen.multithreading;

class Async extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //Thread.interrupted();
            //这个方法会清除当前线程的interrupted状态
            System.out.println("Running");
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                System.out.println("SubThread Interrupted");
            }
        }
    }

    public static void main(String[] args) {
        Async async = new Async();
        async.start();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException exception) {
                System.out.println("Main Thread Interrupted");
            }
        }
        while (async.isAlive()) {
            async.interrupt();
            //<!>之所以在线程仍存活的时候不断申请interrupt
            //是因为如果主线程在子线程阻塞时申请interrupt，会清除其isInterrupted()状态
            //并在子线程阻塞位置抛出InterruptedException
            //从而导致阻塞结束后子线程使用isInterrupted()判断时不能被通知
        }
    }
}
