package com.example.clean.app.web.controller;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.clean.app.web.controller.CommonLinks.customersV2Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(path = { "/", "/api" }, produces = MediaTypes.APPLICATION_JSON_V2_VALUE)
public class IndexControllerV2 {

    @GetMapping
    public ResponseEntity<ResourceSupport> index() {

        final ControllerLinkBuilder indexLink = linkTo(IndexControllerV2.class);

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(indexLink.withSelfRel());
        resourceSupport.add(customersV2Link());

        return ResponseEntity.ok(resourceSupport);
    }
}
