package com.example.clean.app.data.jpa.entity;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public final class Name {

    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;

    public Name() {
    }

    private Name(final String firstName, final String middleName, final String lastName, final String suffix) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
    }

    public static Name create(final String firstName, final String middleName, final String lastName, final String suffix) {
        return new Name(firstName, middleName, lastName, suffix);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(middleName, name.middleName) &&
                Objects.equals(lastName, name.lastName) &&
                Objects.equals(suffix, name.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, suffix);
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
