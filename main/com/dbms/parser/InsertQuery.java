package main.com.dbms.parser;

// For INSERT queries
class InsertQuery extends Query {
    private String columns;
    private String values;

    public InsertQuery(String table, String columns, String values) {
        this.table = table;
        this.columns = columns;
        this.values = values;
    }

    public String getColumns() {
        return columns;
    }

    public String getValues() {
        return values;
    }
}