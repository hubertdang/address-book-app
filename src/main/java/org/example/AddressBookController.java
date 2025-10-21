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

    @GetMapping
    @ResponseBody
    public Iterable<AddressBook> getAddressBooks() {
        return addressBookRepository.findAll();
    }

    @GetMapping("/new") // "/" alone is for listing all address books, so this one will be "/new"
    public String showCreateAddressBookForm(Model model) {
        model.addAttribute("addressBook", new AddressBook());
        return "create-address-book"; // name of html template with a form on it to create a new address book
    }

    @PostMapping
    public String createAddressBook(@ModelAttribute("addressBook") AddressBook addressBook) {
        addressBookRepository.save(addressBook);
        return "redirect:/addressBooks/" + addressBook.getId();
    }

    @GetMapping("/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressBook not found"));
        // "addressBook" is the name/key used in the html template
        model.addAttribute("addressBook", addressBook);
        return "address-book"; // "addressBook" here is the name of the html template (different from above)
    }

    @GetMapping("/{id}/buddies/new")
    public String showAddBuddyInfoForm(@PathVariable Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressBook not found"));
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddyInfo", new BuddyInfo());
        return "add-buddy-info";
    }

    @PostMapping("/{id}/buddies")
    public String addBuddyInfo(@PathVariable Long id, @ModelAttribute BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressBook not found"));
        buddyInfo.setId(null); // because of stupid error with hibernate
        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);
        return "redirect:/addressBooks/" + addressBook.getId();
    }

//    /* Need to make custom endpoint because the endpoint exposed by spring data rest is for the whole buddies list and
//       not individual buddies */
//    @PostMapping("/{id}/buddies")
//    public ResponseEntity<AddressBook> addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddyInfo) {
//        AddressBook addressBook = addressBookRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressBook not found"));
//
//        addressBook.addBuddy(buddyInfo);
//        addressBookRepository.save(addressBook);
//
//        return new ResponseEntity<>(addressBook, HttpStatus.CREATED); // Returns addressBook as JSON
//    }

    /* Need to make custom endpoint because the endpoint exposed by spring data rest is for the whole buddies list and
       not individual buddies */
    @DeleteMapping("/{id}/buddies")
    public ResponseEntity<AddressBook> removeBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AddressBook not found"));

        addressBook.removeBuddy(buddyInfo);
        addressBookRepository.save(addressBook);

        return new ResponseEntity<>(addressBook, HttpStatus.OK); // Returns addressBook as JSON
    }
}
