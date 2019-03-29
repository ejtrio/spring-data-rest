package com.ejtrio.springdatarest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.convert.ConversionService;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public abstract class RepositoryTestBase {

    @LocalServerPort
    int port;

    @Autowired
    public WebApplicationContext context;

    public RequestSpecification spec;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Before
    public void setUp() {
        RestAssured.port = port;

        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(this.restDocumentation))
                .build();
    }

    public RequestFieldsSnippet documentPostUsers() {
        return requestFields(
                fieldWithPath("firstName").description("User's First Name"),
                fieldWithPath("lastName").description("User's Last Name"),
                fieldWithPath("email").description("User's Email Address")
        );
    }

    public ResponseFieldsSnippet documentGetUsers() {
        return responseFields(
                fieldWithPath("firstName").description("User's First Name"),
                fieldWithPath("lastName").description("User's Last Name"),
                fieldWithPath("email").description("User's Email Address"),
                fieldWithPath("_links.self.href").description("HATEOAS Self Reference"),
                fieldWithPath("_links.sdr:user.href").description("HATEOAS User Reference"),
                fieldWithPath("_links.sdr:user.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.sdr:orders.href").description("HATEOAS User Reference"),
                fieldWithPath("_links.sdr:orders.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.curies.[].href").description("HATEOAS CURIE Reference"),
                fieldWithPath("_links.curies.[].name").description("HATEOAS CURIE Name"),
                fieldWithPath("_links.curies.[].templated").description("HATEOAS Generated Code"));
    }

    public RequestFieldsSnippet documentPostItems() {
        return requestFields(
                fieldWithPath("itemName").description("Item's Name"),
                fieldWithPath("description").description("Item's Description"),
                fieldWithPath("price").description("Item's Price")
        );
    }

    public ResponseFieldsSnippet documentGetItems() {
        return responseFields(
                fieldWithPath("itemName").description("Item's Name"),
                fieldWithPath("description").description("Item's Description"),
                fieldWithPath("price").description("Item's Price"),
                fieldWithPath("_links.self.href").description("HATEOAS Self Reference"),
                fieldWithPath("_links.sdr:item.href").description("HATEOAS Item Reference"),
                fieldWithPath("_links.sdr:item.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.sdr:orders.href").description("HATEOAS OrderItem Reference"),
                fieldWithPath("_links.sdr:orders.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.curies.[].href").description("HATEOAS CURIE Reference"),
                fieldWithPath("_links.curies.[].name").description("HATEOAS CURIE Name"),
                fieldWithPath("_links.curies.[].templated").description("HATEOAS Generated Code")
        );
    }

    public RequestFieldsSnippet documentPostOrders() {
        return requestFields(
                fieldWithPath("user").description("Link to User Resource"),
                fieldWithPath("items.[].item").description("Link to Item Resource"),
                fieldWithPath("items.[].quantity").description("Amount ordered")
        );
    }

    public ResponseFieldsSnippet documentGetOrders() {
        return responseFields(
                fieldWithPath("totalPrice").description("Order's Price"),
                fieldWithPath("_links.self.href").description("HATEOAS Self Reference"),
                fieldWithPath("_links.sdr:order.href").description("HATEOAS Order Reference"),
                fieldWithPath("_links.sdr:order.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.sdr:user.href").description("HATEOAS User Reference"),
                fieldWithPath("_links.sdr:user.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.sdr:items.href").description("HATEOAS Items Reference"),
                fieldWithPath("_links.sdr:items.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.curies.[].href").description("HATEOAS CURIE Reference"),
                fieldWithPath("_links.curies.[].name").description("HATEOAS CURIE Name"),
                fieldWithPath("_links.curies.[].templated").description("HATEOAS Generated Code")
        );
    }

    public ResponseFieldsSnippet documentGetOrdersProjection() {
        return responseFields(
                fieldWithPath("totalPrice").description("Order's Price"),
                fieldWithPath("user.fullName").description("User's Full Name"),
                fieldWithPath("user.email").description("User's Email Address"),
                fieldWithPath("user._links.self.href").description("HATEOAS Self Reference"),
                fieldWithPath("user._links.self.templated").description("HATEOAS Generated Code"),
                fieldWithPath("user._links.sdr:orders.href").description("HATEOAS Order Reference"),
                fieldWithPath("user._links.sdr:orders.templated").description("HATEOAS Generated Code"),
                fieldWithPath("items.[].item.itemName").description("Item's Name"),
                fieldWithPath("items.[].item.description").description("Item's Description"),
                fieldWithPath("items.[].item.price").description("Item's Price"),
                fieldWithPath("items.[].quantity").description("Amount ordered"),
                fieldWithPath("items.[]._links.self.href").description("HATEOAS Self Reference"),
                fieldWithPath("items.[]._links.self.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.self.href").description("HATEOAS Self Reference"),
                fieldWithPath("_links.sdr:order.href").description("HATEOAS Order Reference"),
                fieldWithPath("_links.sdr:order.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.sdr:user.href").description("HATEOAS User Reference"),
                fieldWithPath("_links.sdr:user.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.sdr:items.href").description("HATEOAS Items Reference"),
                fieldWithPath("_links.sdr:items.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.curies.[].href").description("HATEOAS CURIE Reference"),
                fieldWithPath("_links.curies.[].name").description("HATEOAS CURIE Name"),
                fieldWithPath("_links.curies.[].templated").description("HATEOAS Generated Code")
        );
    }

    public ResponseFieldsSnippet documentGetOrderItems() {
        return responseFields(
                fieldWithPath("quantity").description("OrderItem's Quantity"),
                fieldWithPath("orderItemId._links.sdr:item.href").description("HATEOAS Item Reference"),
                fieldWithPath("orderItemId._links.sdr:item.templated").description("HATEOAS Generated Code"),
                fieldWithPath("orderItemId._links.sdr:order.href").description("HATEOAS Order Reference"),
                fieldWithPath("orderItemId._links.sdr:order.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.self.href").description("HATEOAS Self Reference"),
                fieldWithPath("_links.sdr:orderItem.href").description("HATEOAS OrderItem Reference"),
                fieldWithPath("_links.sdr:orderItem.templated").description("HATEOAS Generated Code"),
                fieldWithPath("_links.curies.[].href").description("HATEOAS CURIE Reference"),
                fieldWithPath("_links.curies.[].name").description("HATEOAS CURIE Name"),
                fieldWithPath("_links.curies.[].templated").description("HATEOAS Generated Code")
        );
    }
}
