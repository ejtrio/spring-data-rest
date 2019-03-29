package com.ejtrio.springdatarest.infrastructure.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(schema = "SDR", name="\"ORDER\"")
@Data
public class Order extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "orderItemKey.order", cascade = CascadeType.ALL)
    private Collection<OrderItem> items;

    @PrePersist
    @PreUpdate
    void updateOrderItemAssociation() {
        totalPrice = 0.0;
        for(OrderItem orderItem : this.items) {
            totalPrice = totalPrice + orderItem.getOrderItemKey().getItem().getPrice() * orderItem.getQuantity();
            orderItem.getOrderItemKey().setOrder(this);
        }
    }
}
