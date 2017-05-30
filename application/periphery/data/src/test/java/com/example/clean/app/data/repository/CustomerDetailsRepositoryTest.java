package com.example.clean.app.data.repository;

import com.example.clean.app.core.domain.Customer;
import com.example.clean.app.core.domain.Name;
import com.example.clean.app.data.jpa.repository.CustomerJpaRepository;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.example.clean.app.data.helper.JpaRepositoryHelper.customerEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDetailsRepositoryTest {

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    private CustomerDetailsRepository customerDetailsRepository;

    @Before
    public void setUp() throws Exception {
        customerDetailsRepository = new CustomerDetailsRepository(customerJpaRepository);
    }

    @Test
    public void shouldGetCustomers() throws Exception {
        given(customerJpaRepository.findAll()).willReturn(ImmutableList.of(customerEntity()));

        final List<Customer> customers = customerDetailsRepository.customers();

        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSize(1);

        final Name name = customers.get(0).getName();
        assertThat(name.firstName().value()).isEqualTo(customerEntity().getName().getFirstName());
        assertThat(name.lastName().value()).isEqualTo(customerEntity().getName().getLastName());
    }

    @Test
    public void shouldGetCustomer() throws Exception {
        given(customerJpaRepository.findOne(any(Long.class))).willReturn(customerEntity());

        final Customer customer = customerDetailsRepository.customer(Customer.Id.of(123L));

        final Name name = customer.getName();
        assertThat(name.firstName().value()).isEqualTo(customerEntity().getName().getFirstName());
        assertThat(name.lastName().value()).isEqualTo(customerEntity().getName().getLastName());
    }
}