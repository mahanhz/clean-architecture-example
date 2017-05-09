package com.example.clean.app.core.boundary.enter;

import com.example.clean.app.core.domain.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> customers();

    Customer customer(Customer.Id customerId);
}
