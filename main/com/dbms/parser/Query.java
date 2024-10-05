package main.com.dbms.parser;

// Base class for all queries
abstract class Query {
    protected String table;

    public String getTable() {
        return table;
    }
}