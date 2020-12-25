package qiuchi.chen.multithreading;

class SynchronizedDeclaration {
    public synchronized void OneCallEachTime()
            throws InterruptedException {
        System.out.printf("Thread:%s invoked\n", Thread.currentThread().getName());
        Thread.sleep(1000);
        System.out.printf("Thread:%s exits\n", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        SynchronizedDeclaration declaration = new SynchronizedDeclaration();
        for (int i = 0; i < 10; i++) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        declaration.OneCallEachTime();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            })).start();
        }
    }
}
