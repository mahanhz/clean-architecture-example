package com.example.clean.app.data.jpa.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity {

    @Version
    protected int version;
}
