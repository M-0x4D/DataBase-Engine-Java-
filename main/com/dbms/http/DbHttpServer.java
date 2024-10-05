package main.com.dbms.http;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import main.com.dbms.parser.QueryHandler;

public class DbHttpServer {
 
    public void start()throws IOException{
          // Create the HTTP server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Set a default handler for the "/hello" context
        server.createContext("/", new QueryHandler());
        
        // Start the server
        server.start();
        
        System.out.println("Server is running on port 8000...");
    }
}
