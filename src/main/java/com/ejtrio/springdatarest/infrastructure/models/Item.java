package com.ejtrio.springdatarest.infrastructure.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(schema = "SDR", name="ITEM")
@Data
public class Item extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private Double price;

    @OneToMany(mappedBy = "orderItemKey.item")
    private Collection<OrderItem> orders;
}
