package main.com.dbms.parser;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class QueryHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Read the HTTP request body
        String requestBody = readRequestBody(exchange.getRequestBody());
        String response;

        try {
            // Parse the query using the QueryParser
            Query parsedQuery = QueryParser.parseQuery(requestBody);

            // For demonstration, just print the parsed query (you would typically run the query)
            response = "Parsed query: " + parsedQuery.getTable();
            System.out.println(response);

            // For SELECT queries, also print the columns/conditions, etc.
            if (parsedQuery instanceof SelectQuery) {
                SelectQuery selectQuery = (SelectQuery) parsedQuery;
                response += "\nColumns: " + selectQuery.getColumns() + "\nCondition: " + selectQuery.getCondition();
            }
            // For INSERT queries, print the columns and values
            else if (parsedQuery instanceof InsertQuery) {
                InsertQuery insertQuery = (InsertQuery) parsedQuery;
                response += "\nColumns: " + insertQuery.getColumns() + "\nValues: " + insertQuery.getValues();
            }
            // For DELETE queries, print the condition
            else if (parsedQuery instanceof DeleteQuery) {
                DeleteQuery deleteQuery = (DeleteQuery) parsedQuery;
                response += "\nCondition: " + deleteQuery.getCondition();
            }

        } catch (QueryParser.InvalidQueryException e) {
            response = "Invalid query: " + e.getMessage();
        }

        // Send the response
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    // Read the request body
    private String readRequestBody(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
}