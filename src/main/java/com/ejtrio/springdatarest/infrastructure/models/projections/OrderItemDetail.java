package com.ejtrio.springdatarest.infrastructure.models.projections;

import com.ejtrio.springdatarest.infrastructure.models.Item;
import com.ejtrio.springdatarest.infrastructure.models.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "orderItemDetail", types = { OrderItem.class })
public interface OrderItemDetail {

    @Value("#{target.getOrderItemKey().getItem()}")
    Item getItem();

    Integer getQuantity();
}
