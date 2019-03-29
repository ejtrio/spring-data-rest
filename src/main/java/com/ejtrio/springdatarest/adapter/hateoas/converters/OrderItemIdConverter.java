package com.ejtrio.springdatarest.adapter.hateoas.converters;

import com.ejtrio.springdatarest.infrastructure.models.Item;
import com.ejtrio.springdatarest.infrastructure.models.Order;
import com.ejtrio.springdatarest.infrastructure.models.OrderItem;
import com.ejtrio.springdatarest.infrastructure.models.OrderItemKey;
import com.ejtrio.springdatarest.infrastructure.repositories.OrderItemRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class OrderItemIdConverter implements BackendIdConverter {

    private static final String DECODING_ERROR = "Error decoding %s";
    private static final String DESERIALIZATION_ERROR = "Error deserializing %s into OrderItemKey";

    private OrderItemRepository orderItemRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderItemIdConverter(OrderItemRepository orderItemRepository
            , ObjectMapper objectMapper)
    {
        this.orderItemRepository = orderItemRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Serializable fromRequestId(String id, Class<?> entityType) {
        if (id == null) {
            return null;
        }

        if (!id.contains(" ") && id.toUpperCase().contains("%7B")) {
            try {
                id = URLDecoder.decode(id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(String.format(DESERIALIZATION_ERROR, id));
            }
        }

        OrderItemKey orderItemKey = new OrderItemKey();
        try {
            JsonNode jsonNode = objectMapper.readTree(id);
            Order order = new Order();
            order.setOrderId(jsonNode.get("orderId").asLong());
            Item item = new Item();
            item.setItemId(jsonNode.get("itemId").asLong());
            orderItemKey.setOrder(order);
            orderItemKey.setItem(item);
        } catch (IOException ex) {
            throw new RuntimeException(String.format(DESERIALIZATION_ERROR, id));
        }

        return orderItemRepository.findById(orderItemKey).orElse(null);
    }

    @Override
    public String toRequestId(Serializable id, Class<?> entityType) {
        if (id == null) {
            return null;
        }

        OrderItemKey orderItemKey = (OrderItemKey) id;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("orderId", orderItemKey.getOrder().getOrderId());
        objectNode.put("itemId", orderItemKey.getItem().getItemId());
        String jsonString = objectNode.toString();

        return jsonString;
    }

    @Override
    public boolean supports(Class<?> type) {
        return OrderItem.class.equals(type);
    }
}
