package main.com.dbms;

import java.io.IOException;
import main.com.dbms.http.DbHttpServer;

public class Main {
    public static void main(String[] args) throws IOException {
        DbHttpServer server = new DbHttpServer();
        server.start();
        // String data = "test";
        // byte[] byteArray = data.getBytes();
        // DiskHandler handler = new DiskHandler();
        // handler.store(byteArray);
    }
}
