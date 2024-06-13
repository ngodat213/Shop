package com.hutech.Shop.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String notes;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}