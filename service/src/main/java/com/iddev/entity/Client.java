package com.iddev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = "orders")
@ToString(exclude = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User {

    private Integer age;

    private String driverLicenseNumber;

    private String passportNumber;

    private String phoneNumber;

    @Builder.Default
    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();


}
