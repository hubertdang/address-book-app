package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/addressBooks")
public class AddressBookController {
    private final AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping
    public Iterable<AddressBook> getAddressBooks() {
        return addressBookRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressBook createAddressBook(@ModelAttribute AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }

    @GetMapping("/{id}")
    public AddressBook getAddressBook(@PathVariable Long id) {
        return addressBookRepository.findById(id)
                .orElseThrow((() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddressBook(@PathVariable Long id) {
        addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        addressBookRepository.deleteById(id);
    }

    @GetMapping("/{id}/buddies")
    public Iterable<BuddyInfo> getBuddies(@PathVariable Long id) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return addressBook.getBuddies();
    }

    @PostMapping("/{id}/buddies")
    @ResponseStatus(HttpStatus.CREATED)
    public BuddyInfo addBuddy(@PathVariable Long id, @ModelAttribute BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        buddyInfo.setId(null);
        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);
        return buddyInfo;
    }

    @DeleteMapping("/{addressBookId}/buddies/{buddyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBuddy(@PathVariable Long addressBookId, @PathVariable Long buddyId) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        boolean removed = addressBook.getBuddies().removeIf(b -> b.getId().equals(buddyId));
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        addressBookRepository.save(addressBook);
    }
}
