package com.ejtrio.springdatarest.infrastructure.repositories;

import com.ejtrio.springdatarest.RepositoryTestBase;
import com.ejtrio.springdatarest.utils.JsonHelper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class OrderItemRepositoryTests extends RepositoryTestBase {

    @Before
    public void loadData() throws Exception {
        given(this.spec)
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/user.json"))
                .post("/users");
        given(this.spec)
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/item.json"))
                .post("/items");
        given(this.spec)
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/order.json"))
                .post("/orders");
    }

    @Test
    public void postOrderItem() throws Exception {
        Response response = given(this.spec)
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/orderItem.json"))
                .post("/orderItems");

        System.out.println(response.asString());

        assertThat(response.getStatusCode()).isEqualTo(405);
    }

    @Test
    public void getOrderItem() {
        Response response = given(this.spec)
                .filter(document("getOrderItems", preprocessResponse(prettyPrint()), documentGetOrderItems()))
                .get("/orderItems/%7B%22orderId%22:1,%22itemId%22:1%7D");

        assertThat(response.getStatusCode()).isEqualTo(200);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("quantity")).isInstanceOf(Integer.class);
    }

    @Test
    public void deleteOrderItem() {
        Response response = given(this.spec)
                .delete("/orderItems/%7B%22orderId%22:1,%22itemId%22:1%7D");

        assertThat(response.getStatusCode()).isEqualTo(405);
    }
}
