package com.uladzimirbaklan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/proxy/{path}")
    public ResponseEntity<String> getProxyRequest(@PathVariable String path)
        throws URISyntaxException, IOException, InterruptedException {
        final var request = HttpRequest
            .newBuilder()
            .uri(new URI(path))
            .build();

        final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.ok(response.body());
    }

    public record TestResponse(String id, String version) {
    }

}
