package main.com.dbms.parser;

import java.util.regex.*;

public class QueryParser {

    // Regular expressions for different SQL statements (basic SELECT, INSERT, DELETE)
    private static final String SELECT_PATTERN = "(?i)^SELECT\\s+(\\*|[\\w,\\s]+)\\s+FROM\\s+(\\w+)\\s*(WHERE\\s+.+)?$";
    private static final String INSERT_PATTERN = "^INSERT\\s+INTO\\s+(\\w+)\\s+\\((.+)\\)\\s+VALUES\\s+\\((.+)\\)$";
    private static final String DELETE_PATTERN = "^DELETE\\s+FROM\\s+(\\w+)\\s*(WHERE\\s+.+)?$";

    public static Query parseQuery(String query) throws InvalidQueryException {
        query = query.trim().toUpperCase(); // Normalize the query (uppercase for simplicity)

         // Match SELECT queries
         Matcher selectMatcher = Pattern.compile(SELECT_PATTERN).matcher(query);
         
         if (selectMatcher.matches()) {
             String columns = selectMatcher.group(1);
             String table = selectMatcher.group(2);
             String condition = (selectMatcher.group(3) != null) ? selectMatcher.group(3).substring(6) : null; // Remove "WHERE" part
 
             System.out.println("Parsed SELECT query:");
             System.out.println("Table: " + table);
             System.out.println("Columns: " + columns);
             System.out.println("Condition: " + condition);
 
             return new SelectQuery(table, columns, condition);
         }

        // Match INSERT queries
        Matcher insertMatcher = Pattern.compile(INSERT_PATTERN).matcher(query);
        if (insertMatcher.matches()) {
            String table = insertMatcher.group(1);
            String columns = insertMatcher.group(2);
            String values = insertMatcher.group(3);

            return new InsertQuery(table, columns, values);
        }

        // Match DELETE queries
        Matcher deleteMatcher = Pattern.compile(DELETE_PATTERN).matcher(query);
        if (deleteMatcher.matches()) {
            String table = deleteMatcher.group(1);
            String condition = (deleteMatcher.group(2) != null) ? deleteMatcher.group(2).substring(6) : null; // Remove "WHERE" part

            return new DeleteQuery(table, condition);
        }

        // If the query does not match any patterns, throw an exception
        throw new InvalidQueryException("Invalid query: " + query);
    }

    // Custom exception class for invalid queries
    public static class InvalidQueryException extends Exception {
        public InvalidQueryException(String message) {
            super(message);
        }
    }
}
