package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testDefault() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("addressBooks", "profile");
    }

    @Test
    void testGetAllAddressBook() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/addressBooks", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetAddressBookById() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/addressBooks/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetNonExistentAddressBookById() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/addressBooks/10", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testShowCreateAddressBookForm() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/addressBooks/new", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testCreateAddressBookReturnsRedirect() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/addressBooks", null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testShowAddBuddyInfoForm() {
        ResponseEntity<String> response = restTemplate.getForEntity("/addressBooks/1/buddies/new", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddBuddyInfo() {
        BuddyInfo buddy = new BuddyInfo("John Doe", "123 Street", "555-1234");
        ResponseEntity<String> response =
                restTemplate.postForEntity("/addressBooks/1/buddies", buddy, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
