package syr.design.chat.utils.juc;

public class ThreadFactory implements java.util.concurrent.ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
