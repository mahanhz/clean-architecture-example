module com.example.clean.app.adapter {
    requires com.example.clean.app.core;

    requires org.apache.commons.lang3;
    requires jackson.annotations;

    exports com.example.clean.app.adapter.web;
    exports com.example.clean.app.adapter.web.api;
}