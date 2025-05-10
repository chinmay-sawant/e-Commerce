package com.learn.ecom;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.learn.ecom.utils.utils;

@AutoConfigureMockMvc
public abstract class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    protected String token;

    @BeforeEach
    public void setUpToken() {
        // Generate the token once
        token = utils.getAuth0TokenFromPropertiesTest();
    }
}