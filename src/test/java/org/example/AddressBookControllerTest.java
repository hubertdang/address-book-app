package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    /**
     * For making life easier.
     * @return The base URL of the addressBook REST API
     */
    private String baseUrl() {
        return "http://localhost:" + port + "/api/addressBooks";
    }

    @Test
    void getAllAddressBooksReturnsOk() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void createAddressBookReturnsCreated() {
        AddressBook ab = new AddressBook();
        ResponseEntity<AddressBook> response = restTemplate.postForEntity(baseUrl(), ab, AddressBook.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getAddressBookByIdReturnsOkOrNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/1", String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteAddressBookReturnsNoContentOrNotFound() {
        restTemplate.delete(baseUrl() + "/1"); // might exist or not
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getBuddiesReturnsOkOrNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/1/buddies", String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @Test
    void addBuddyReturnsCreatedOrNotFound() {
        BuddyInfo buddy = new BuddyInfo("John", "123 Street", "555-1234");
        ResponseEntity<BuddyInfo> response =
                restTemplate.postForEntity(baseUrl() + "/1/buddies", buddy, BuddyInfo.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteBuddyReturnsNoContentOrNotFound() {
        restTemplate.delete(baseUrl() + "/1/buddies/1"); // might exist or not
        ResponseEntity<String> response =
                restTemplate.getForEntity(baseUrl() + "/1/buddies", String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.NOT_FOUND);
    }
}
