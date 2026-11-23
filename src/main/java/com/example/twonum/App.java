package com.example.twonum;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    static HttpServer start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.start();
        return server;
    }

    public static void main(String[] args) throws IOException {
        String p = System.getenv("PORT");
        start(p == null ? 8080 : Integer.parseInt(p));
    }
}
