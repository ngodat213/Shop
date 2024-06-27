package com.hutech.Shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Catology")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAT")
    private Long id;

    @Column(name = "NAME_CAT", nullable = false)
    private String name;

    @Column(name = "META")
    private String meta;

    @Column(name = "`ORDER`", nullable = false)
    private int order;

    @Column(name = "LINK")
    private String link;

    @Column(name = "HIDE", nullable = false)
    private boolean hide;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
