package com.example.clean.app.core.boundary.enter;

import com.example.clean.app.core.domain.Customer;

public interface CustomerService {

    Customer customer(Customer.Id customerId);
}
