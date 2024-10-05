package main.com.dbms.parser;

// For DELETE queries
class DeleteQuery extends Query {
    private String condition;

    public DeleteQuery(String table, String condition) {
        this.table = table;
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }
}