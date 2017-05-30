package com.example.clean.app.web.controller;

import com.example.clean.app.adapter.web.CustomerAdapter;
import com.example.clean.app.adapter.web.CustomerDTOFactory;
import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
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
import static com.example.clean.app.helper.DomainModelHelper.customer;
import static com.example.clean.app.helper.RestHelper.parseJson;
import static com.example.clean.app.web.controller.CommonLinks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    public static final String API_CUSTOMERS_PATH = "/api/customers";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerAdapter customerAdapter;

    @Test
    public void shouldGetCustomers() throws Exception {

        given(customerAdapter.customers()).willReturn(customersDTO());

        final Object document = parseJson(mvc, API_CUSTOMERS_PATH);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF,
                                                   REL_HOME,
                                                   REL_CREATE,
                                                   REL_CUSTOMER_PREFIX + customer().getId().getId());
    }

    @Test
    public void shouldGetCustomer() throws Exception {

        given(customerAdapter.customer(any(Long.class))).willReturn(customerDTO());

        final Object document = parseJson(mvc, API_CUSTOMERS_PATH + "/123");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF,
                                                   REL_HOME,
                                                   REL_CUSTOMERS,
                                                   REL_UPDATE,
                                                   REL_DELETE);
    }

    @Test
    public void shouldCreateCustomer() throws Exception {

        final ObjectMapper mapper = new ObjectMapper();
        final CustomerDTO customerDTO = CustomerDTOFactory.customer(customer());

        final String json = mapper.writeValueAsString(customerDTO);

        final ResultActions result = mvc.perform(post(API_CUSTOMERS_PATH).contentType(MediaTypes.APPLICATION_JSON_V1)
                                                                         .accept(MediaTypes.APPLICATION_JSON_V1)
                                                                         .content(json))
                                        .andExpect(status().isCreated());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(API_CUSTOMERS_PATH);
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {

        final ObjectMapper mapper = new ObjectMapper();
        final CustomerDTO customerDTO = CustomerDTOFactory.customer(customer());

        final String json = mapper.writeValueAsString(customerDTO);

        final String uri = API_CUSTOMERS_PATH + "/123";
        final ResultActions result = mvc.perform(put(uri).contentType(MediaTypes.APPLICATION_JSON_V1)
                                                         .accept(MediaTypes.APPLICATION_JSON_V1)
                                                         .content(json))
                                        .andExpect(status().isOk());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(API_CUSTOMERS_PATH);
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {

        final String uri = API_CUSTOMERS_PATH + "/123";
        final ResultActions result = mvc.perform(delete(uri).accept(MediaTypes.APPLICATION_JSON_V1))
                                        .andExpect(status().isOk());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(API_CUSTOMERS_PATH);
    }
}