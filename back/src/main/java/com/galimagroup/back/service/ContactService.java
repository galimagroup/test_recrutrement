package com.galimagroup.back.service;

import org.springframework.stereotype.Service;
import com.galimagroup.back.model.Contact;
import com.galimagroup.back.repository.ContactRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {
    
    private final ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        contact.setCreatedAt(System.currentTimeMillis());
        return contactRepository.save(contact);
    }
}
