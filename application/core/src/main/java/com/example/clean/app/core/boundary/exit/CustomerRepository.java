package com.example.clean.app.core.boundary.exit;

import com.example.clean.app.core.domain.Customer;

public interface CustomerRepository {

    Customer customer(Customer.Id customerId);
}
