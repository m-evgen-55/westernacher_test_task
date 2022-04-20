package com.westernacher.test.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "User first name")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "User last name")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "User email")
    private String email;

    @Column(name = "birth_date", nullable = false, columnDefinition = "User birth date")
    private Date birthDate;
}
