package com.example.clean.app.schema;

import com.example.clean.app.adapter.web.CustomerAdapter;
import com.example.clean.app.helper.RestHelper;
import com.example.clean.app.web.controller.CustomerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.clean.app.helper.AdapterHelper.customerDTO;
import static com.example.clean.app.helper.AdapterHelper.customersDTO;
import static com.example.clean.app.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static com.example.clean.app.web.controller.CustomerControllerTest.API_CUSTOMERS_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerSchemaValidatorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerAdapter customerAdapter;

    @Test
    public void shouldValidateCustomersSchema() throws Exception {

        given(customerAdapter.customers()).willReturn(customersDTO());

        final ResultActions result = RestHelper.get(mvc, API_CUSTOMERS_PATH);

        assertSuccessfulSchemaValidation(result, "CustomersDTOSchema.json");
    }

    @Test
    public void shouldValidateCustomerSchema() throws Exception {

        given(customerAdapter.customer(any(Long.class))).willReturn(customerDTO());

        final ResultActions result = RestHelper.get(mvc, API_CUSTOMERS_PATH + "/123");

        assertSuccessfulSchemaValidation(result, "CustomerDTOSchema.json");
    }
}