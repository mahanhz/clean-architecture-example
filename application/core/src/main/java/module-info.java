module com.example.clean.app.core {
    requires org.apache.commons.lang3;

    exports com.example.clean.app.core.boundary.enter;
    exports com.example.clean.app.core.boundary.exit;
    exports com.example.clean.app.core.domain;
    exports com.example.clean.app.core.usecase;
}