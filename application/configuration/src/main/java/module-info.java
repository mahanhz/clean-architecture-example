module com.example.clean.app.configuration {
    requires com.example.clean.app.adapter;
    requires com.example.clean.app.core;
    requires com.example.clean.app.periphery.data;
    requires com.example.clean.app.periphery.web;

    requires spring.beans;
    requires spring.context;
    requires springfox.swagger2;
    requires springfox.spring.web;
    requires springfox.spi;
    //requires springfox.core;
    requires springfox.swagger.common;

    exports com.example.clean.app.configuration;
}