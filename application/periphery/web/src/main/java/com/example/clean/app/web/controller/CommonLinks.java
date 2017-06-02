package com.example.clean.app.web.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public final class CommonLinks {

    public static final String REL_HOME = "home";
    public static final String REL_CUSTOMERS = "customers";
    public static final String REL_CREATE = "create";
    public static final String REL_UPDATE = "update";
    public static final String REL_DELETE = "delete";
    public static final String REL_CUSTOMER_PREFIX = "customer-";

    private CommonLinks() {
        // Prevent instantiation
    }

    public static Link homeLink() {
        final ControllerLinkBuilder homeLink = linkTo(IndexController.class);
        return homeLink.withRel(REL_HOME);
    }

    public static Link customersLink() {
        final ControllerLinkBuilder customersLink = linkTo(methodOn(CustomerController.class).customers());
        return customersLink.withRel(REL_CUSTOMERS);
    }

    public static Link customersV2Link() {
        final ControllerLinkBuilder customersLink = linkTo(methodOn(CustomerControllerV2.class).customers());
        return customersLink.withRel(REL_CUSTOMERS);
    }
}
