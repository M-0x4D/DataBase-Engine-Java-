package main.com.dbms.TwoPhaseLock;
import java.util.*;

class LockManager {
    private Map<String, List<Lock>> locks = new HashMap<>();

    // Request a lock for a transaction
    public synchronized boolean acquireLock(String transactionId, String resourceId, LockType type) {
        List<Lock> currentLocks = locks.getOrDefault(resourceId, new ArrayList<>());

        // Check if the lock can be granted
        boolean canGrant = true;
        for (Lock lock : currentLocks) {
            if (lock.transactionId.equals(transactionId)) {
                // Transaction already holds a lock of this type
                return true;
            } else if (lock.type == LockType.EXCLUSIVE || type == LockType.EXCLUSIVE) {
                // Conflict detected: exclusive lock conflict
                canGrant = false;
                break;
            }
        }

        if (canGrant) {
            currentLocks.add(new Lock(transactionId, type));
            locks.put(resourceId, currentLocks);
            return true;
        }

        // Could not acquire the lock
        return false;
    }

    // Release a lock for a transaction
    public synchronized void releaseLock(String transactionId, String resourceId) {
        List<Lock> currentLocks = locks.get(resourceId);

        if (currentLocks != null) {
            currentLocks.removeIf(lock -> lock.transactionId.equals(transactionId));

            // If no locks remain on the resource, remove it from the map
            if (currentLocks.isEmpty()) {
                locks.remove(resourceId);
            }
        }
    }
}
