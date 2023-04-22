package com.example.crud.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table
public class Meal {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private boolean isAvailable;

    // AVOID BY-DIRECTIONAL MAPPING BECAUSE OF INCONSISTENCY SO ADD ON "manytoone" col only https://www.baeldung.com/hibernate-one-to-many
    // bidirectional mapping
    // @OneToMany
    // @JoinColumn(name="chef_id")
    // private Set<Chef> chefs;

}
