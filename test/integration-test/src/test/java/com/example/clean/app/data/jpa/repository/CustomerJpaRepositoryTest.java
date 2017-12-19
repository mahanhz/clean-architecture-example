package com.example.clean.app.data.jpa.repository;

import com.example.clean.app.data.jpa.entity.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerJpaRepositoryTest {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Test
    public void shouldGetCustomer() throws Exception {
        final Optional<CustomerEntity> entity = customerJpaRepository.findById(1L);

        assertThat(entity).isPresent();
        assertThat(entity.get().getName().getFirstName()).isEqualTo("John");
        assertThat(entity.get().getName().getLastName()).isEqualTo("Doe");
    }
}