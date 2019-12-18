package org.eclipse.aether.util.concurrency;

import java.util.concurrent.atomic.*;
import java.util.*;
import java.util.concurrent.locks.*;

public final class RunnableErrorForwarder
{
    private final Thread thread;
    private final AtomicInteger counter;
    private final AtomicReference<Throwable> error;
    
    public RunnableErrorForwarder() {
        super();
        this.thread = Thread.currentThread();
        this.counter = new AtomicInteger();
        this.error = new AtomicReference<Throwable>();
    }
    
    public Runnable wrap(final Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable cannot be null");
        this.counter.incrementAndGet();
        return new Runnable(this, runnable) {
            final Runnable val$runnable;
            final RunnableErrorForwarder this$0;
            
            RunnableErrorForwarder$1(final RunnableErrorForwarder this$0, final Runnable val$runnable) {
                this.this$0 = this$0;
                this.val$runnable = val$runnable;
                super();
            }
            
            @Override
            public void run() {
                try {
                    this.val$runnable.run();
                }
                catch (RuntimeException ex) {}
                catch (Error error) {
                    RunnableErrorForwarder.access$000(this.this$0).compareAndSet(null, error);
                    throw error;
                }
                finally {
                    RunnableErrorForwarder.access$100(this.this$0).decrementAndGet();
                    LockSupport.unpark(RunnableErrorForwarder.access$200(this.this$0));
                }
            }
        };
    }
    
    public void await() {
        this.awaitTerminationOfAllRunnables();
        final Throwable t = this.error.get();
        if (t == null) {
            return;
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
        }
        if (t instanceof ThreadDeath) {
            throw new IllegalStateException(t);
        }
        if (t instanceof Error) {
            throw (Error)t;
        }
        throw new IllegalStateException(t);
    }
    
    private void awaitTerminationOfAllRunnables() {
        if (!this.thread.equals(Thread.currentThread())) {
            throw new IllegalStateException("wrong caller thread, expected " + this.thread + " and not " + Thread.currentThread());
        }
        boolean b = false;
        while (this.counter.get() > 0) {
            LockSupport.park();
            if (Thread.interrupted()) {
                b = true;
            }
        }
        if (b) {
            Thread.currentThread().interrupt();
        }
    }
    
    static AtomicReference access$000(final RunnableErrorForwarder runnableErrorForwarder) {
        return runnableErrorForwarder.error;
    }
    
    static AtomicInteger access$100(final RunnableErrorForwarder runnableErrorForwarder) {
        return runnableErrorForwarder.counter;
    }
    
    static Thread access$200(final RunnableErrorForwarder runnableErrorForwarder) {
        return runnableErrorForwarder.thread;
    }
}
