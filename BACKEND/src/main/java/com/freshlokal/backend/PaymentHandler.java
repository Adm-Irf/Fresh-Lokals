package com.freshlokal.backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class PaymentHandler implements HttpHandler {
    public PaymentHandler() {
    }

    public void handle(HttpExchange var1) throws IOException {
        if ("POST".equals(var1.getRequestMethod())) {
            String var2 = "{\"message\": \"Payment created successfully\"}";
            var1.getResponseHeaders().add("Content-Type", "application/json");
            var1.sendResponseHeaders(200, (long)var2.getBytes().length);

            try (OutputStream var3 = var1.getResponseBody()) {
                var3.write(var2.getBytes());
            }
        } else {
            var1.sendResponseHeaders(405, -1L);
        }

    }
}
