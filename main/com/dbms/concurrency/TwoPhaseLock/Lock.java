package main.com.dbms.TwoPhaseLock;
class Lock {
    String transactionId;
    LockType type;

    Lock(String transactionId, LockType type) {
        this.transactionId = transactionId;
        this.type = type;
    }
}