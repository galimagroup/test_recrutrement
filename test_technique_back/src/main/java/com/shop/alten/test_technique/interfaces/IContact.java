package com.shop.alten.test_technique.interfaces;

import com.shop.alten.test_technique.model.Contact;

import java.util.List;

public interface IContact {
    Contact createContact(Contact contact);
    Contact getContactById(Long id);
    List<Contact> getAllContacts();
    Contact updateContact(Long id, Contact contact);
    void deleteContact(Long id);
}
