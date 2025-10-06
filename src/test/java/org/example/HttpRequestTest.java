package org.example;

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
        assertThat(response.getBody()).contains("Ronny", "Johnny", "Bonnie");
    }

    @Test
    void testGetNonExistentAddressBookById() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/addressBooks/10", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateAddressBook() {
        HttpEntity<AddressBook> request = new HttpEntity<>(new AddressBook());

        ResponseEntity<AddressBook> response = restTemplate.exchange(
                "http://localhost:" + port + "/addressBooks", HttpMethod.POST, request, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AddressBook newAddressBook = response.getBody();
        assertThat(newAddressBook).isNotNull();
    }

    @Test
    void testAddBuddyInfoToAddressBook() {
        BuddyInfo buddyInfo = new BuddyInfo("Hubert", "Ottawa", "613");

        HttpEntity<BuddyInfo> request = new HttpEntity<>(buddyInfo);
        ResponseEntity<AddressBook> response = restTemplate.exchange(
                "http://localhost:" + port + "/addressBooks/1/buddies", HttpMethod.POST, request, AddressBook.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AddressBook newAddressBook = response.getBody();
        assertThat(newAddressBook).isNotNull();
        assertThat(newAddressBook.getBuddies()).contains(buddyInfo);
    }
}
