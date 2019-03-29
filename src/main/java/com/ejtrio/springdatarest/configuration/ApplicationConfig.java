package com.ejtrio.springdatarest.configuration;

import com.ejtrio.springdatarest.adapter.hateoas.hal.CustomCurieProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class ApplicationConfig implements WebMvcConfigurer {

    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/public/html5/");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public CurieProvider curieProvider() {
        // Normally a DefaultCurieProvider would suffice here, but I chose Item as an entity name :(
        return new CustomCurieProvider("sdr", new UriTemplate(baseUrl + "/docs/{rel}.html"));
    }
}
