package com.example.clean.app.data.repository;

import com.example.clean.app.data.jpa.repository.CustomerJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.clean.app.data.helper.DomainModelHelper.customer;
import static com.example.clean.app.data.helper.JpaRepositoryHelper.customerEntity;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerOperationsRepositoryTest {

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    private CustomerOperationsRepository customerOperationsRepository;

    @Before
    public void setUp() throws Exception {
        customerOperationsRepository = new CustomerOperationsRepository(customerJpaRepository);
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        customerOperationsRepository.create(customer());

        verify(customerJpaRepository, times(1)).save(customerEntity(customer()));
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        customerOperationsRepository.update(customer());

        verify(customerJpaRepository, times(1)).save(customerEntity(customer()));
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        customerOperationsRepository.delete(customer().getId());

        verify(customerJpaRepository, times(1)).delete(customer().getId().getId());
    }
}