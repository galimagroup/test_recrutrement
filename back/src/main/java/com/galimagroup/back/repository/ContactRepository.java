package com.galimagroup.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.galimagroup.back.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
