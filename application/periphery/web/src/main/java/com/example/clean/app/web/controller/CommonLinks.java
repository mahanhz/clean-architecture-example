package com.example.clean.app.web.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public final class CommonLinks {

    public static final String REL_HOME = "home";

    private CommonLinks() {
        // Prevent instantiation
    }

    public static Link homeLink() {
        final ControllerLinkBuilder homeLink = linkTo(IndexController.class);
        return homeLink.withRel(REL_HOME);
    }

    public static Link customersLink() {
        final ControllerLinkBuilder customersLink = linkTo(methodOn(CustomerController.class).customers());
        return customersLink.withRel("customers");
    }
}
