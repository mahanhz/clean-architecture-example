package com.example.clean.app.data.jpa.repository;

import com.example.clean.app.data.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
}
