package com.shop.alten.test_technique.repository;


import com.shop.alten.test_technique.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
