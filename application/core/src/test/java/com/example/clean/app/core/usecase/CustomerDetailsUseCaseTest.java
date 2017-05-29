package com.example.clean.app.core.usecase;

import com.example.clean.app.core.boundary.exit.CustomerRepository;
import com.example.clean.app.core.domain.Customer;
import com.example.clean.app.core.domain.Name;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.example.clean.app.core.helper.DomainModelHelper.customer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDetailsUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerDetailsUseCase customerDetailsUseCase;

    @Before
    public void setUp() throws Exception {
        customerDetailsUseCase = new CustomerDetailsUseCase(customerRepository);
    }

    @Test
    public void shouldGetCustomers() throws Exception {
        given(customerRepository.customers()).willReturn(ImmutableList.of(customer()));

        final List<Customer> customers = customerDetailsUseCase.customers();

        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSize(1);

        final Name name = customers.get(0).getName();
        assertThat(name.firstName()).isEqualTo(customer().getName().firstName());
        assertThat(name.lastName()).isEqualTo(customer().getName().lastName());
    }

    @Test
    public void shouldGetCustomer() throws Exception {
        given(customerRepository.customer(any())).willReturn(customer());

        final Customer customer = customerDetailsUseCase.customer(Customer.Id.of(123L));

        final Name name = customer.getName();
        assertThat(name.firstName()).isEqualTo(customer().getName().firstName());
        assertThat(name.lastName()).isEqualTo(customer().getName().lastName());
    }
}