package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/addressBooks")
public class AddressBookViewController {
    private final AddressBookRepository addressBookRepository;

    public AddressBookViewController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping
    public String getAddressBooks(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "all-address-books";
    }

    @GetMapping("/new")
    public String getCreateAddressBookForm(Model model) {
        model.addAttribute("addressBook", new AddressBook());
        return "create-address-book";
    }

    @GetMapping("/{id}")
    public String getAddressBookView(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("addressBook", addressBook);
        return "address-book";
    }

    @GetMapping("/{id}/buddies/new")
    public String getAddBuddyInfoForm(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddyInfo", new BuddyInfo());
        return "add-buddy-info";
    }
}
