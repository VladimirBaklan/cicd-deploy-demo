package com.uladzimirbaklan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@RestController
public class TestController {
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @GetMapping("/test")
    public ResponseEntity<TestResponse> getTestResponse() {
        return ResponseEntity.ok(new TestResponse(UUID.randomUUID().toString(), "version_2"));
    }

    @PostMapping("/proxy")
    public ResponseEntity<String> getProxyRequest(@RequestBody ProxyRequestBody requestBody)
        throws URISyntaxException, IOException, InterruptedException {
        final var request = HttpRequest
            .newBuilder()
            .uri(new URI(requestBody.uri))
            .build();

        final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.ok(response.body());
    }

    public record ProxyRequestBody(String uri) {}

    public record TestResponse(String id, String version) {
    }

}
