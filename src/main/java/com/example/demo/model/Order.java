package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "OrderList")
public class Order {
    // PK
    @Id
    @Column(name="order_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderNo;

    // User pk
    @Column(name = "user_id")
    private String UserId;

    // Purchase pk
    @Column(name = "purchase_no")
    private Integer purchaseNo;
}