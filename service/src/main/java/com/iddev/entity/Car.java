package com.iddev.entity;

import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "orders")
@ToString(exclude = "orders")
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CarBrand brand;

    private String model;

    private Integer manufactureYear;

    @Enumerated(EnumType.STRING)
    private CarCategory category;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    private Integer price;

    private Boolean isAvailable;

    private String image;

    @Builder.Default
    @OneToMany(mappedBy = "car")
    private List<Order> orders = new ArrayList<>();
}
