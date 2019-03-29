package com.ejtrio.springdatarest.infrastructure.models.serializers;

import com.ejtrio.springdatarest.infrastructure.models.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.hateoas.UriTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;

public class OrderDeserializer extends StdDeserializer<Order> {

    private static final TypeDescriptor URI_DESCRIPTOR = TypeDescriptor.valueOf(URI.class);
    private static final String UNEXPECTED_VALUE = "Expected URI cause property name %s points to the managed domain type!";
    private static final String RESOURCE_NOT_FOUND = "Resource not found for URI %s";

    private ConversionService conversionService;

    @Autowired
    public OrderDeserializer(ConversionService conversionService) {
        this(null, conversionService);
    }

    @Autowired
    public OrderDeserializer(Class<?> vc, ConversionService conversionService) {
        super(vc);
        this.conversionService = conversionService;
    }

    @Override
    public Order deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        Order order = new Order();
        User user = (User) convertFromUri("user", node, User.class, ctxt);
        order.setUser(user);

        Collection<OrderItem> orderItems = new HashSet<>();
        order.setItems(orderItems);
        node.get("items").forEach((n) -> {
            try {
                OrderItemKey orderItemKey = new OrderItemKey();
                Item item = (Item) convertFromUri("item", n, Item.class, ctxt);
                orderItemKey.setItem(item);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemKey(orderItemKey);
                orderItem.setQuantity(n.get("quantity").asInt());
                orderItems.add(orderItem);
            } catch (JsonMappingException jme) {
                throw new RuntimeException(jme);
            }
        });

        return order;
    }

    private Object convertFromUri(String property, JsonNode node, Class clazz, DeserializationContext ctxt)
            throws JsonMappingException, RuntimeException {
        String source = node.get(property).asText();

        if (!StringUtils.hasText(source)) {
            return null;
        }

        try {
            URI uri = new UriTemplate(source).expand();
            TypeDescriptor typeDescriptor = TypeDescriptor.valueOf(clazz);

            Object resource = conversionService.convert(uri, URI_DESCRIPTOR, typeDescriptor);
            if(resource == null) {
                throw new RuntimeException(String.format(RESOURCE_NOT_FOUND, source));
            }
            return resource;
        } catch (IllegalArgumentException ex) {
            throw ctxt.weirdStringException(source, URI.class, String.format(UNEXPECTED_VALUE, property));
        }
    }
}
