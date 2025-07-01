package ru.netology.conditional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {

    static GenericContainer<?> devApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    static GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void testDevProfileResponse() {
        String baseUrl = "http://localhost:" + devApp.getMappedPort(8080);
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/profile", String.class);
        assertEquals("Current profile is dev", response.getBody());
    }

    @Test
    void testProdProfileResponse() {
        String baseUrl = "http://localhost:" + prodApp.getMappedPort(8081);
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/profile", String.class);
        assertEquals("Current profile is production", response.getBody());
    }
}
