package com.root.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.root.order.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
