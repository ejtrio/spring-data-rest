package com.ejtrio.springdatarest.infrastructure.models.projections;

import com.ejtrio.springdatarest.infrastructure.models.Order;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "orderDetail", types = { Order.class })
public interface OrderDetail {

    UserDetail getUser();

    Double getTotalPrice();

    Collection<OrderItemDetail> getItems();
}
