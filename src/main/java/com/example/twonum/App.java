package com.example.twonum;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class App {

    static HttpServer start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/add", App::add);
        server.createContext("/health", ex -> {
            if (!"GET".equals(ex.getRequestMethod())) {
                send(ex, 405, "{\"error\":\"method not allowed\"}");
                return;
            }
            send(ex, 200, "{\"status\":\"UP\"}");
        });
        server.start();
        return server;
    }

    private static void add(HttpExchange ex) throws IOException {
        if (!"GET".equals(ex.getRequestMethod())) {
            send(ex, 405, "{\"error\":\"method not allowed\"}");
            return;
        }
        Map<String, String> q = query(ex.getRequestURI().getRawQuery());
        String a = q.get("a");
        String b = q.get("b");
        if (a == null || b == null) {
            send(ex, 400, error("both numbers are required"));
            return;
        }
        long x;
        long y;
        try {
            x = Long.parseLong(a);
        } catch (NumberFormatException e) {
            send(ex, 400, error("the value \"" + a + "\" is not a valid whole number"));
            return;
        }
        try {
            y = Long.parseLong(b);
        } catch (NumberFormatException e) {
            send(ex, 400, error("the value \"" + b + "\" is not a valid whole number"));
            return;
        }
        try {
            send(ex, 200, "{\"sum\":" + Math.addExact(x, y) + "}");
        } catch (ArithmeticException e) {
            send(ex, 400, error("the result is out of range"));
        }
    }

    private static Map<String, String> query(String raw) {
        Map<String, String> m = new HashMap<>();
        if (raw == null) {
            return m;
        }
        for (String pair : raw.split("&")) {
            int i = pair.indexOf('=');
            if (i < 0) {
                continue;
            }
            m.put(URLDecoder.decode(pair.substring(0, i), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(i + 1), StandardCharsets.UTF_8));
        }
        return m;
    }

    private static String error(String msg) {
        return "{\"error\":\"" + msg.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}";
    }

    private static void send(HttpExchange ex, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json");
        ex.sendResponseHeaders(status, bytes.length);
        try (var os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    public static void main(String[] args) throws IOException {
        String p = System.getenv("PORT");
        start(p == null ? 8080 : Integer.parseInt(p));
    }
}
