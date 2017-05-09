package com.example.clean.app.core.domain;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class Customer {

    private final Id id;
    private final Name name;

    private Customer(final Id id, final Name name) {
        this.id = notNull(id);
        this.name = notNull(name);
    }

    public static Customer of(final Id id, final Name name) {
        return new Customer(id, name);
    }

    public Id getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    public static class Id {

        private long id;

        private Id(final long id) {
            this.id = id;
        }

        public static Id of(final long id) {
            return new Id(id);
        }

        public long getId() {
            return id;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Id id1 = (Id) o;
            return id == id1.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "id=" + id +
                    '}';
        }
    }
}
