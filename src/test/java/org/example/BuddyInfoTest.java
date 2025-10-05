package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuddyInfoTest {
    static BuddyInfo buddyInfo;

    @BeforeEach
    public void setUp() throws Exception {
        buddyInfo = new BuddyInfo("Jane Doe", "123 Waterway", "6137461499");
    }

    @Test
    public void getName() {
        assertEquals("Jane Doe",  buddyInfo.getName());
    }

    @Test
    public void setName() {
        buddyInfo.setName("Hubert");
        assertEquals("Hubert",  buddyInfo.getName());
    }

    @Test
    public void getAddress() {
        assertEquals("123 Waterway",  buddyInfo.getAddress());
    }

    @Test
    public void setAddress() {
        buddyInfo.setAddress("131 Fireway");
        assertEquals("131 Fireway",  buddyInfo.getAddress());
    }

    @Test
    public void getPhoneNumber() {
        assertEquals("6137461499",  buddyInfo.getPhoneNumber());
    }

    @Test
    public void setPhoneNumber() {
        buddyInfo.setPhoneNumber("987654321");
        assertEquals("987654321",  buddyInfo.getPhoneNumber());
    }

    @Test
    public void testEquals() {
        BuddyInfo buddyInfo2 = new BuddyInfo("Jane Doe", "123 Waterway", "6137461499");
        assertEquals(buddyInfo2, buddyInfo);
    }

    @Test
    public void testToString() {
        String expected = "BuddyInfo{name='Jane Doe', address='123 Waterway', phoneNumber='6137461499'}";
        assertEquals(expected, buddyInfo.toString());
    }
}