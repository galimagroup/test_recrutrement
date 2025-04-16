package com.galimagroup.back.controller;

import com.galimagroup.back.dto.ContactDto;
import com.galimagroup.back.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDto> submitContact(@Valid @RequestBody ContactDto contactDto) {
        ContactDto savedContact = contactService.saveContact(contactDto);
        return ResponseEntity.status(201).body(savedContact);
    }
}
