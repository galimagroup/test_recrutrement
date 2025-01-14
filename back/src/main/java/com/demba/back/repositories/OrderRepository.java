package com.demba.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demba.back.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
