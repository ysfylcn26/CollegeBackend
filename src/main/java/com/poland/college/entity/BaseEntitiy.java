package com.poland.college.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseEntitiy {

    @Column(name = "uuid", updatable = false, nullable = false)
    @GeneratedValue
    private String uuid = UUID.randomUUID().toString();

}
