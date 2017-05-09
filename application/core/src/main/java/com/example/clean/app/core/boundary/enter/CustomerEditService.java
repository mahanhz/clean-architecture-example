package com.example.clean.app.core.boundary.enter;

import com.example.clean.app.core.domain.Customer;

public interface CustomerEditService {

    void create(Customer customer);

    void update(Customer customer);

    void delete(Customer.Id customerId);
}
