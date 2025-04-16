package com.galimagroup.back.service;

import com.galimagroup.back.dto.ContactDto;
import com.galimagroup.back.model.Contact;
import com.galimagroup.back.repository.ContactRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor    
public class ContactService {

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactDto saveContact(ContactDto contactDto) {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());

        Contact savedContact = contactRepository.save(contact);
        return modelMapper.map(savedContact, ContactDto.class);
    }
}
