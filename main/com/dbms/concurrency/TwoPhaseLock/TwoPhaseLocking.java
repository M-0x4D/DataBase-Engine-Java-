package main.com.dbms.TwoPhaseLock;
public class TwoPhaseLocking {

    void lock(String transaction ,String resource ) {
        LockManager lockManager = new LockManager();

        // T tries to acquire a shared lock on the resource
        if (lockManager.acquireLock(transaction, resource, LockType.SHARED)) {
            System.out.println(transaction + " acquired a shared lock on " + resource);
        }

        // T releases its lock
        lockManager.releaseLock(transaction, resource);
        System.out.println(transaction + " released its lock on " + resource);
    }

}
