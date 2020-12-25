package qiuchi.chen.multithreading;

import java.util.concurrent.locks.ReentrantLock;

class ProgramEntry {
    static class AsyncClass extends Thread {
        public static ReentrantLock reentLock;

        @Override
        public void run() {
            reentLock.lock();
            System.out.println("CurrentThread:" + Thread.currentThread().getName() + ",Lock Acquired");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            System.out.println("CurrentThread:" + Thread.currentThread().getName() + ",Lock Relinquished");
            reentLock.unlock();
        }
    }

    public static void main(String[] args) {
        //<?>可重入锁和他的condition区别
        //可简单地实现在无需来回获取释放锁的情况下，对某状态进行等待
        AsyncClass.reentLock = new ReentrantLock();
        //AsyncClass.reentLock.newCondition().signal();
        //try {
        //    while(some condition)
        //        AsyncClass.reentLock.newCondition().await();<^>注意await()用法
        //} catch (InterruptedException exception) {
        //    exception.printStackTrace();
        //}
        //<^>想想锁一般怎么组织
        //<->一般可重入锁地条件判断是在进锁前还是进锁后？:进锁之后，因为进锁前判断的话，进锁后条件可能不满足
        //一个有锁的代码块中一般只能放一个条件么（和synchronized的唯一的condition对比）
        for (int i = 0; i < 10; i++) {
            AsyncClass asyncClass = new AsyncClass();
            asyncClass.start();
        }
    }
}