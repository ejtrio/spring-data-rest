package com.ejtrio.springdatarest.infrastructure.models;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class OrderItemKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @Override
    public String toString() {
        return new StringBuilder()
                .append("OrderItemKey(")
                .append("orderId=").append(this.order.getOrderId())
                .append(", itemId=").append(this.item.getItemId())
                .append(")")
                .toString();
    }
}
