package com.example.clean.app.helper;

import com.example.clean.app.adapter.web.CustomerDTOFactory;
import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomersDTO;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.example.clean.app.helper.DomainModelHelper.customer;

public final class AdapterHelper {

    private AdapterHelper() {
        // To prevent instantiation
    }

    public static CustomerDTO customerDTO() {
        return CustomerDTOFactory.customer(customer());
    }

    public static CustomersDTO customersDTO() {
        final List<CustomerDTO> customers = CustomerDTOFactory.customers(ImmutableList.of(customer()));

        return new CustomersDTO(customers);
    }
}
