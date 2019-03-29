package com.ejtrio.springdatarest.infrastructure.repositories;

import com.ejtrio.springdatarest.infrastructure.models.OrderItem;
import com.ejtrio.springdatarest.infrastructure.models.OrderItemKey;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, OrderItemKey> {

    @RestResource(exported = false)
    OrderItem save(OrderItem orderItem);

    @RestResource(exported = false)
    void delete(OrderItem orderItem);
}
