package monitor;

import com.google.common.util.concurrent.Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Monitor 类似 synchronized ReentrantLock
 */
public class Demo {

    //使用synchronized
    static class SafeBox1<V> {
        private V value;

        public synchronized V get() throws InterruptedException {
            while (value == null) {
                wait();
            }
            V result = value;
            value = null;
            notifyAll();
            return result;
        }

        public synchronized void set(V newValue) throws InterruptedException {
            while (value != null) {
                wait();
            }
            value = newValue;
            notifyAll();
        }
    }

    //使用 ReentrantLock
    static class SafeBox2<V> {
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition valuePresent = lock.newCondition();
        private final Condition valueAbsent = lock.newCondition();
        private V value;

        public V get() throws InterruptedException {
            lock.lock();
            try {
                while (value == null) {
                    valuePresent.await();
                }
                V result = value;
                value = null;
                valueAbsent.signal();
                return result;
            } finally {
                lock.unlock();
            }
        }

        public void set(V newValue) throws InterruptedException {
            lock.lock();
            try {
                while (value != null) {
                    valueAbsent.await();
                }
                value = newValue;
                valuePresent.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    //使用Monitor
    static class SafeBox3<V> {
        private final Monitor monitor = new Monitor();
        private final Monitor.Guard valuePresent = new Monitor.Guard(monitor) {
            public boolean isSatisfied() {
                return value != null;
            }
        };
        private final Monitor.Guard valueAbsent = new Monitor.Guard(monitor) {
            public boolean isSatisfied() {
                return value == null;
            }
        };
        private V value;

        public V get() throws InterruptedException {
            monitor.enterWhen(valuePresent);
            try {
                V result = value;
                value = null;
                return result;
            } finally {
                monitor.leave();
            }
        }

        public void set(V newValue) throws InterruptedException {
            monitor.enterWhen(valueAbsent);
            try {
                value = newValue;
            } finally {
                monitor.leave();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SafeBox3 safeBox3=new SafeBox3<String>();
        safeBox3.set("123");
        System.out.println("get: "+safeBox3.get());
    }
}
