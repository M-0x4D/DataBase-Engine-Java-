package main.com.dbms.TwoPhaseLock;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;

public class Transaction {
    private final LockManager lockManager;
    private final Set<String> acquiredLocks = new HashSet<>();
    private boolean isActive = true;
    private static final Logger logger = Logger.getLogger(Transaction.class.getName());

    public Transaction(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    public void lockRead(String resource, long timeout) throws InterruptedException {
        if (!isActive) throw new IllegalStateException("Transaction is not active.");
        if (lockManager.acquireSharedLock(resource, timeout)) {
            acquiredLocks.add(resource);
        } else {
            rollback(); // Rollback if lock acquisition fails to avoid deadlocks
        }
    }

    public void lockWrite(String resource, long timeout) throws InterruptedException {
        if (!isActive) throw new IllegalStateException("Transaction is not active.");
        if (lockManager.acquireExclusiveLock(resource, timeout)) {
            acquiredLocks.add(resource);
        } else {
            rollback(); // Rollback if lock acquisition fails to avoid deadlocks
        }
    }

    public void commit() {
        releaseLocks();
        isActive = false;
        log("Transaction committed by thread " + Thread.currentThread().getName());
    }

    public void rollback() {
        releaseLocks();
        isActive = false;
        log("Transaction rolled back by thread " + Thread.currentThread().getName());
    }

    private void releaseLocks() {
        for (String resource : acquiredLocks) {
            lockManager.releaseExclusiveLock(resource);
        }
        acquiredLocks.clear();
    }

    private void log(String message) {
        logger.info(message);
    }
}

