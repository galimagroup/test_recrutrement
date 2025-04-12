package com.galimagroup.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.galimagroup.back.model.Contact;
import com.galimagroup.back.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> submitContact(@Valid @RequestBody Contact contact) {
        return ResponseEntity.ok(contactService.saveContact(contact));
    }
}
