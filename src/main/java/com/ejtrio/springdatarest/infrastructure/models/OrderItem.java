package com.ejtrio.springdatarest.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "SDR", name="ORDER_ITEM")
@Data
public class OrderItem extends AuditInfo {

    @EmbeddedId
    @JsonProperty("orderItemId")
    private OrderItemKey orderItemKey;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Override
    public String toString() {
        return new StringBuilder()
                .append("OrderItem(")
                .append("orderItemKey=").append(this.orderItemKey.toString())
                .append(", quantity=").append(this.quantity)
                .append(")")
                .toString();
    }
}
