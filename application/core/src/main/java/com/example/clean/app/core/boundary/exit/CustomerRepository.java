package com.example.clean.app.core.boundary.exit;

import com.example.clean.app.core.domain.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> customers();

    Customer customer(Customer.Id customerId);
}
