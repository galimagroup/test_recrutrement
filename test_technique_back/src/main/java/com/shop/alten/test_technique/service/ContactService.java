package com.shop.alten.test_technique.service;

import com.shop.alten.test_technique.interfaces.IContact;
import com.shop.alten.test_technique.model.Contact;
import com.shop.alten.test_technique.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService implements IContact {
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id " + id));
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact updateContact(Long id, Contact contact) {
        Contact existingContact = getContactById(id);
        existingContact.setEmail(contact.getEmail());
        existingContact.setMessage(contact.getMessage());
        existingContact.setHandled(contact.isHandled());
        return contactRepository.save(existingContact);
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
