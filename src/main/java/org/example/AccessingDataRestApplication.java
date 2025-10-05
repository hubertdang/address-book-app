package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class AccessingDataRestApplication {
    private static final Logger log = LoggerFactory.getLogger(AccessingDataRestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepository) {
        return args -> {
            BuddyInfo johnny = new BuddyInfo("Johnny", "Canada", "123");
            BuddyInfo bonnie = new BuddyInfo("Bonnie", "UK", "456");
            BuddyInfo jerry = new BuddyInfo("Jerry", "Jamaica", "111");
            BuddyInfo julie = new BuddyInfo("Julie", "US", "098");
            BuddyInfo ronny = new BuddyInfo("Ronny", "India", "555");

            AddressBook addressBook1 = new AddressBook();
            addressBook1.addBuddy(johnny);
            addressBook1.addBuddy(bonnie);

            AddressBook addressBook2 = new AddressBook();
            addressBook2.addBuddy(jerry);
            addressBook2.addBuddy(julie);
            addressBook2.addBuddy(ronny);

            // save an address books
            addressBookRepository.save(addressBook1);
            addressBookRepository.save(addressBook2);

            // fetch all address books
            log.info("Address books found with findAll():");
            log.info("-------------------------------");
            addressBookRepository.findAll().forEach(addressBook-> {
                log.info(addressBook.toString());
            });
            log.info("");

            Optional<AddressBook> addressBook;

            // fetch an individual address book by ID
            addressBook = addressBookRepository.findById(1L);
            log.info("Address book found with findById(1L):");
            log.info("--------------------------------");
            log.info(addressBook.toString());
            log.info("");

            // fetch an individual address book by ID
            addressBook = addressBookRepository.findById(2L);
            log.info("Address book found with findById(2L):");
            log.info("--------------------------------");
            log.info(addressBook.toString());
            log.info("");
        };
    }
}
