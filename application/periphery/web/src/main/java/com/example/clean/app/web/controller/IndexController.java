package com.example.clean.app.web.controller;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.clean.app.web.controller.CommonLinks.customersLink;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(path = { "/", "/api" }, produces = MediaTypes.APPLICATION_JSON_V1_VALUE)
public class IndexController {

    @GetMapping
    public ResponseEntity<ResourceSupport> index() {

        final ControllerLinkBuilder indexLink = linkTo(IndexController.class);

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(indexLink.withSelfRel());
        resourceSupport.add(customersLink());

        return ResponseEntity.ok(resourceSupport);
    }
}
