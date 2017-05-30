package com.example.clean.app.adapter.web;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomersDTO;
import com.example.clean.app.core.boundary.enter.CustomerEditService;
import com.example.clean.app.core.boundary.enter.CustomerService;
import com.example.clean.app.core.domain.Customer;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.clean.app.adapter.helper.DomainModelHelper.customer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerAdapterTest {

    @Mock
    CustomerService customerService;

    @Mock
    CustomerEditService customerEditService;

    private CustomerAdapter customerAdapter;

    @Before
    public void setUp() throws Exception {
        customerAdapter = new CustomerAdapter(customerService, customerEditService);
    }

    @Test
    public void shouldGetCustomers() throws Exception {
        given(customerService.customers()).willReturn(ImmutableList.of(customer()));

        final CustomersDTO customersDTO = customerAdapter.customers();

        assertThat(customersDTO.customers).hasSize(1);

        final CustomerDTO customerDTO = customersDTO.customers.get(0);
        assertThat(customerDTO.name.firstName).isEqualTo(customer().getName().firstName().value());
        assertThat(customerDTO.name.lastName).isEqualTo(customer().getName().lastName().value());
    }

    @Test
    public void shouldGetCustomer() throws Exception {
        given(customerService.customer(any(Customer.Id.class))).willReturn(customer());

        final CustomerDTO customerDTO = customerAdapter.customer(123L);

        assertThat(customerDTO.name.firstName).isEqualTo(customer().getName().firstName().value());
        assertThat(customerDTO.name.lastName).isEqualTo(customer().getName().lastName().value());
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        final CustomerDTO customerDTO = CustomerDTOFactory.customer(customer());

        customerAdapter.create(customerDTO);

        verify(customerEditService, times(1)).create(CustomerFactory.customer(customerDTO));
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        final CustomerDTO customerDTO = CustomerDTOFactory.customer(customer());

        customerAdapter.update(customerDTO);

        verify(customerEditService, times(1)).update(CustomerFactory.customer(customerDTO));
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        customerAdapter.delete(customer().getId().getId());

        verify(customerEditService, times(1)).delete(customer().getId());
    }
}