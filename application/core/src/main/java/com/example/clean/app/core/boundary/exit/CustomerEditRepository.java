package com.example.clean.app.core.boundary.exit;

import com.example.clean.app.core.domain.Customer;

public interface CustomerEditRepository {

    void create(Customer customer);

    void update(Customer customer);

    void delete(Customer.Id customerId);
}
