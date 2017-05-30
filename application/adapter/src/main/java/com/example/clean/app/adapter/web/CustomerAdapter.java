package com.example.clean.app.adapter.web;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomersDTO;
import com.example.clean.app.core.boundary.enter.CustomerEditService;
import com.example.clean.app.core.boundary.enter.CustomerService;
import com.example.clean.app.core.domain.Customer;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomerAdapter {

    private CustomerService customerService;
    private CustomerEditService customerEditService;

    public CustomerAdapter(final CustomerService customerService, final CustomerEditService customerEditService) {
        this.customerService = notNull(customerService);
        this.customerEditService = notNull(customerEditService);
    }

    public CustomersDTO customers() {
        final List<Customer> customers = customerService.customers();

        return new CustomersDTO(CustomerDTOFactory.customers(customers));
    }

    public CustomerDTO customer(final long customerId) {
        final Customer customer = customerService.customer(Customer.Id.of(customerId));

        return CustomerDTOFactory.customer(customer);
    }

    public void create(final CustomerDTO customerDTO) {
        notNull(customerDTO);

        customerEditService.create(CustomerFactory.customer(customerDTO));
    }

    public void update(final CustomerDTO customerDTO) {
        notNull(customerDTO);

        customerEditService.update(CustomerFactory.customer(customerDTO));
    }

    public void delete(final long customerId) {
        customerEditService.delete(Customer.Id.of(customerId));
    }
}
