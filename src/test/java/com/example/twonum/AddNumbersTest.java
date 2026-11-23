package com.example.twonum;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AddNumbersTest {

    private static com.sun.net.httpserver.HttpServer server;
    private static String base;
    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void start() throws Exception {
        server = App.start(0);
        base = "http://localhost:" + server.getAddress().getPort();
    }

    @AfterAll
    static void stop() {
        server.stop(0);
    }

    private static HttpResponse<String> get(String pathAndQuery) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create(base + pathAndQuery)).GET().build(),
                HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void ac1_addsTwoPositives() throws Exception {
        var r = get("/add?a=2&b=3");
        assertEquals(200, r.statusCode());
        assertEquals("{\"sum\":5}", r.body());
    }

    @Test
    void ac2_addsNegatives() throws Exception {
        var r = get("/add?a=-7&b=4");
        assertEquals(200, r.statusCode());
        assertEquals("{\"sum\":-3}", r.body());
    }

    @Test
    void ac3_addsZero() throws Exception {
        var r = get("/add?a=0&b=0");
        assertEquals(200, r.statusCode());
        assertEquals("{\"sum\":0}", r.body());
    }

    @Test
    void ac4_rejectsNonNumeric() throws Exception {
        var r = get("/add?a=abc&b=3");
        assertEquals(400, r.statusCode());
        assertEquals("{\"error\":\"the value \\\"abc\\\" is not a valid whole number\"}", r.body());
    }

    @Test
    void ac5_rejectsMissingNumber() throws Exception {
        var r = get("/add?a=3");
        assertEquals(400, r.statusCode());
        assertEquals("{\"error\":\"both numbers are required\"}", r.body());
    }

    @Test
    void ac6_rejectsOverflow() throws Exception {
        var r = get("/add?a=9223372036854775807&b=1");
        assertEquals(400, r.statusCode());
        assertEquals("{\"error\":\"the result is out of range\"}", r.body());
    }

    @Test
    void ac7_rejectsDecimal() throws Exception {
        var r = get("/add?a=1.5&b=2");
        assertEquals(400, r.statusCode());
        assertEquals("{\"error\":\"the value \\\"1.5\\\" is not a valid whole number\"}", r.body());
    }

    @Test
    void nfr2_healthReturnsUp() throws Exception {
        var r = get("/health");
        assertEquals(200, r.statusCode());
        assertEquals("{\"status\":\"UP\"}", r.body());
    }
}
