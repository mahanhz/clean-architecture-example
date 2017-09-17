package com.example.clean.app.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.clean.app.web.controller.CommonLinks.homeLink;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(produces = MediaTypes.APPLICATION_JSON_V1_VALUE)
public class SampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    @GetMapping(path = "/secure")
    public ResponseEntity<ResourceSupport> secure() {

        LOGGER.info(marker("SECURITY"), "This is a secure resource");

        final ControllerLinkBuilder link = linkTo(methodOn(SampleController.class).secure());

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(link.withSelfRel());
        resourceSupport.add(homeLink());

        return ResponseEntity.ok(resourceSupport);
    }

    @GetMapping(path = "/audit")
    public ResponseEntity<ResourceSupport> audit() {

        LOGGER.info(marker("AUDIT"), "This is an auditable resource");

        final ControllerLinkBuilder link = linkTo(methodOn(SampleController.class).audit());

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(link.withSelfRel());
        resourceSupport.add(homeLink());

        return ResponseEntity.ok(resourceSupport);
    }

    private Marker marker(final String name) {
        return MarkerFactory.getMarker(name);
    }
}
