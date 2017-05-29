package com.example.clean.app.core.usecase;

import com.example.clean.app.core.boundary.exit.CustomerEditRepository;
import com.example.clean.app.core.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.clean.app.core.helper.DomainModelHelper.customer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerEditUseCaseTest {

    @Mock
    private CustomerEditRepository customerEditRepository;

    private CustomerEditUseCase customerEditUseCase;

    @Before
    public void setUp() throws Exception {
        customerEditUseCase = new CustomerEditUseCase(customerEditRepository);
    }

    @Test
    public void shouldCreateCustomer() throws Exception {

        final Customer customer = customer();
        customerEditUseCase.create(customer);

        verify(customerEditRepository, times(1)).create(customer);
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {

        final Customer customer = customer();
        customerEditUseCase.update(customer);

        verify(customerEditRepository, times(1)).update(customer);
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {

        final Customer customer = customer();
        customerEditUseCase.delete(customer.getId());

        verify(customerEditRepository, times(1)).delete(customer.getId());
    }
}
