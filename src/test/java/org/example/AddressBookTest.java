package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressBookTest {
    static AddressBook addressBook;

    @BeforeEach
    public void setUp() throws Exception {
        addressBook = new AddressBook();
    }

    @Test
    public void testAddressBook() {
        assertTrue(addressBook.isEmpty());
    }

    @Test
    public void testAddBuddy() {
        BuddyInfo buddy = new BuddyInfo();
        addressBook.addBuddy(new BuddyInfo());
        assertTrue(addressBook.hasBuddy(buddy));
    }

    @Test
    public void testRemoveBuddy() {
        BuddyInfo buddy = new BuddyInfo();
        addressBook.addBuddy(new BuddyInfo());
        addressBook.removeBuddy(buddy);
        assertFalse(addressBook.hasBuddy(buddy));
    }

    @Test
    public void testHasBuddy() {
        BuddyInfo buddy = new BuddyInfo();
        addressBook.addBuddy(new BuddyInfo());
        assertTrue(addressBook.hasBuddy(buddy));
        addressBook.removeBuddy(buddy);
        assertFalse(addressBook.hasBuddy(buddy));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(addressBook.isEmpty());
        BuddyInfo buddy = new BuddyInfo();
        addressBook.addBuddy(new BuddyInfo());
        assertFalse(addressBook.isEmpty());
    }
}