package main.com.dbms.TwoPhaseLock;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import java.util.logging.*;

public class LockManager {
    private final Map<String, ReentrantReadWriteLock> locks = new HashMap<>();
    private final Map<Thread, Set<String>> threadLocks = new HashMap<>();
    private static final Logger logger = Logger.getLogger(LockManager.class.getName());

    // Initialize lock for each resource
    public void initLock(String resource) {
        locks.put(resource, new ReentrantReadWriteLock());
    }

    // Acquire a shared lock with timeout
    public boolean acquireSharedLock(String resource, long timeout) throws InterruptedException {
        ReentrantReadWriteLock lock = locks.get(resource);
        if (lock != null) {
            boolean acquired = lock.readLock().tryLock(timeout, TimeUnit.MILLISECONDS);
            if (acquired) {
                log("Shared lock acquired on " + resource + " by thread " + Thread.currentThread().getName());
                addLockToThread(Thread.currentThread(), resource);
                return true;
            } else {
                log("Failed to acquire shared lock on " + resource + " by thread " + Thread.currentThread().getName());
            }
        }
        return false;
    }

    // Acquire an exclusive lock with timeout
    public boolean acquireExclusiveLock(String resource, long timeout) throws InterruptedException {
        ReentrantReadWriteLock lock = locks.get(resource);
        if (lock != null) {
            boolean acquired = lock.writeLock().tryLock(timeout, TimeUnit.MILLISECONDS);
            if (acquired) {
                log("Exclusive lock acquired on " + resource + " by thread " + Thread.currentThread().getName());
                addLockToThread(Thread.currentThread(), resource);
                return true;
            } else {
                log("Failed to acquire exclusive lock on " + resource + " by thread " + Thread.currentThread().getName());
            }
        }
        return false;
    }

    // Release a shared lock
    public void releaseSharedLock(String resource) {
        ReentrantReadWriteLock lock = locks.get(resource);
        if (lock != null && holdsLock(Thread.currentThread(), resource)) {
            lock.readLock().unlock();
            log("Shared lock released on " + resource + " by thread " + Thread.currentThread().getName());
            removeLockFromThread(Thread.currentThread(), resource);
        }
    }

    // Release an exclusive lock
    public void releaseExclusiveLock(String resource) {
        ReentrantReadWriteLock lock = locks.get(resource);
        if (lock != null && holdsLock(Thread.currentThread(), resource)) {
            lock.writeLock().unlock();
            log("Exclusive lock released on " + resource + " by thread " + Thread.currentThread().getName());
            removeLockFromThread(Thread.currentThread(), resource);
        }
    }

    // Helper method to add a lock to a thread's lock set
    private synchronized void addLockToThread(Thread thread, String resource) {
        threadLocks.computeIfAbsent(thread, k -> new HashSet<>()).add(resource);
    }

    // Helper method to remove a lock from a thread's lock set
    private synchronized void removeLockFromThread(Thread thread, String resource) {
        Set<String> resources = threadLocks.get(thread);
        if (resources != null) {
            resources.remove(resource);
            if (resources.isEmpty()) {
                threadLocks.remove(thread);
            }
        }
    }

    // Helper method to check if a thread holds a lock on a resource
    private synchronized boolean holdsLock(Thread thread, String resource) {
        Set<String> resources = threadLocks.get(thread);
        return resources != null && resources.contains(resource);
    }

    // Log actions and state
    private void log(String message) {
        logger.info(message);
    }
}
