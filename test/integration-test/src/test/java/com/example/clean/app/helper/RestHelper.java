package com.example.clean.app.helper;

import com.example.clean.app.web.controller.MediaTypes;
import com.jayway.jsonpath.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestHelper {

    public static final String COUNTRY = "se";

    private RestHelper() {
    }

    public static ResultActions get(final MockMvc mvc, final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url))
                  .andExpect(status().isOk())
                  .andExpect(content().contentTypeCompatibleWith(MediaTypes.APPLICATION_JSON_V1));
    }

    public static Object parseJson(final MockMvc mvc, final String url) throws Exception {
        final ResultActions result = get(mvc, url);
        final String content = result.andReturn().getResponse().getContentAsString();

        return Configuration.defaultConfiguration().jsonProvider().parse(content);
    }
}
