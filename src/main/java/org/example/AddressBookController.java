package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/addressBooks")
public class AddressBookController {
    private final AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/{id}")
    public String addressBook(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressBook not found"));
        model.addAttribute("addressBook", addressBook);
        return "addressbook";
    }

    @PostMapping("/{id}/buddies")
    public ResponseEntity<AddressBook> addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressBook not found"));

        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);

        return new ResponseEntity<>(addressBook, HttpStatus.CREATED);
    }
}
