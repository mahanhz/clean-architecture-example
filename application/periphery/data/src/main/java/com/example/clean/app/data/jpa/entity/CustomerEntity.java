package com.example.clean.app.data.jpa.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "customer_t")
public class CustomerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Embedded
    private Name name;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(final Name name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CustomerEntity that = (CustomerEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
