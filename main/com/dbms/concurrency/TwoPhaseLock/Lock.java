package main.com.dbms.TwoPhaseLock;
class Lock {

    private LockType lockType;
    private String transactionId;

    public Lock(LockType lockType, String transactionId) {
        this.lockType = lockType;
        this.transactionId = transactionId;
    }

    public LockType getLockType() {
        return lockType;
    }

    public String getTransactionId() {
        return transactionId;
    }
}