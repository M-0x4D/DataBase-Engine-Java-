package main.com.dbms.parser;

// For SELECT queries
class SelectQuery extends Query {
    private String columns;
    private String condition;

    public SelectQuery(String table, String columns, String condition) {
        this.table = table;
        this.columns = columns;
        this.condition = condition;
    }

    public String getColumns() {
        return columns;
    }

    public String getCondition() {
        return condition;
    }
}