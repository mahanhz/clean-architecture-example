module com.example.clean.app.periphery.web {
    requires com.example.clean.app.adapter;

    requires java.validation;
    requires micrometer.core;
    requires org.apache.commons.lang3;
    requires slf4j.api;
    requires spring.beans;
    requires spring.hateoas;
    requires spring.web;

    exports com.example.clean.app.web.controller;
}