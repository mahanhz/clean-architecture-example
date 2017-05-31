package com.example.clean.app.web.controller.exception;

import com.example.clean.app.web.controller.MediaTypes;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ErrorHandlerController.class)
public class ErrorHandlerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldDisplayNotFoundError() throws Exception {

        final ResultActions result = mvc.perform(get("/error").requestAttr("javax.servlet.error.status_code", 404))
                                        .andExpect(status().isNotFound())
                                        .andExpect(content().contentTypeCompatibleWith(MediaTypes.APPLICATION_VND_ERROR_JSON));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        final JSONArray messages = JsonPath.read(document, "$..message");
        assertThat(messages).contains("404");
    }
}