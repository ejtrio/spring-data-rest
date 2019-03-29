package com.ejtrio.springdatarest.configuration;

import com.ejtrio.springdatarest.infrastructure.models.Order;
import com.ejtrio.springdatarest.infrastructure.models.OrderItem;
import com.ejtrio.springdatarest.infrastructure.models.serializers.OrderDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    @Qualifier("defaultConversionService")
    private ConversionService conversionService;

    /**
     * Uncomment if you want to expose all entity ids on json
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(OrderItem.class);
//        config.exposeIdsFor(
//                entityManager.getMetamodel().getEntities().stream()
//                .map(Type::getJavaType)
//                .toArray(Class[]::new));
    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new SimpleModule("CustomModule") {

            @Override
            public void setupModule(SetupContext context) {
                SimpleDeserializers deserializers = new SimpleDeserializers();
                deserializers.addDeserializer(Order.class, new OrderDeserializer(conversionService));
                context.addDeserializers(deserializers);
            }
        });
    }
}
