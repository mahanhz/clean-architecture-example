module com.example.clean.app.periphery.data {
    requires com.example.clean.app.core;

    requires hibernate.jpa;
    requires org.apache.commons.lang3;
    requires spring.data.jpa;

    exports com.example.clean.app.data.jpa.repository;
    exports com.example.clean.app.data.repository;
}