var AddressBooks = {
    setup: function() {
        AddressBooks.getAllAddressBooks();
        $('#createAddressBookForm').on('submit', AddressBooks.createAddressBook);
        $('#viewAddressBookForm').on('submit', AddressBooks.viewAddressBook);
        $('#addBuddyInfoForm').on('submit', AddressBooks.addBuddyInfo);
    },

    getAllAddressBooks: function() {
        $.ajax({
            type: 'GET',
            url: '/api/addressBooks',
            dataType: 'json',
            success: AddressBooks.showAddressBooks,
            error: function(xhrObj, textStatus, exception) {
                console.error('Failed to load address books:', textStatus, exception);
            }
        })
    },

    showAddressBooks: function(data, requestStatus, xhrObj) {
        let list = $('#addressBookList');
        list.empty();

        if (data.length === 0) {
            list.append('<p>No address books found.</p>');
        } else {
            data.forEach(function(ab) {
                list.append('<li>' + ab.id + ' - Owner: ' + ab.ownerName + '</li>');
            });
        }
    },

    viewAddressBook: function(event) {
        event.preventDefault();

        const id = $('#viewAddressBookId').val();

        $.ajax({
            type: 'GET',
            url: `/api/addressBooks/${id}/buddies`,
            dataType: 'json',
            success: AddressBooks.showAddressBook,
            error: function(xhrObj, textStatus, exception) {
                console.error('Failed to load address book:', textStatus, exception);
            }
        })
    },

    showAddressBook: function(data, requestStatus, xhrObj) {
        let list = $('#buddyList');
        list.empty();

        if (data.length === 0) {
            list.append('<li>No buddies found.</li>');
        } else {
            data.forEach(function(b) {
                list.append('<li>' + b.name + ' | ' + b.phoneNumber + ' | ' + b.address + '</li>');
            });
        }
    },

    createAddressBook: function(event) {
        event.preventDefault();

        const ownerName = $('#ownerName').val();

        $.ajax({
            type: 'POST',
            url: '/api/addressBooks',
            dataType: 'json',
            data: { ownerName: ownerName},
            success: function(newAddressBook) {
                $('#ownerName').val(''); // clear form
                AddressBooks.getAllAddressBooks();
            },
            error: function(xhrObj, textStatus, exception) {
                console.error('Failed to load address books:', textStatus, exception);
            }
        })
    },

    addBuddyInfo: function(event) {
        event.preventDefault();

        const addressBookId = $('#addressBookId').val();
        const buddyName = $('#buddyName').val();
        const buddyPhone = $('#buddyPhone').val();
        const buddyAddress = $('#buddyAddress').val();

        $.ajax({
            type: 'POST',
            url: `/api/addressBooks/${addressBookId}/buddies`,
            dataType: 'json',
            data: {
                name: buddyName,
                phoneNumber: buddyPhone,
                address: buddyAddress,
            },
            success: function(newBuddyInfo) {
                $('#addBuddyInfoForm')[0].reset(); // clear form
            },
            error: function(xhrObj, textStatus, exception) {
                console.error('Failed to load address books:', textStatus, exception);
            }
        })
    }
};

$(AddressBooks.setup);