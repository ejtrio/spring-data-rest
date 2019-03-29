package com.ejtrio.springdatarest.infrastructure.repositories;

import com.ejtrio.springdatarest.RepositoryTestBase;
import com.ejtrio.springdatarest.infrastructure.models.Item;
import com.ejtrio.springdatarest.utils.JsonHelper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class OrderRepositoryTests extends RepositoryTestBase {

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
    public void postOrder() throws Exception {
        Response response = given(this.spec)
                .filter(document("postOrders", preprocessResponse(prettyPrint()), documentPostOrders()))
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/order.json"))
                .post("/orders");

        assertThat(response.getStatusCode()).isEqualTo(201);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("totalPrice")).isInstanceOf(Double.class);
    }

    @Test
    public void getOrder() {
        Response response = given(this.spec)
                .filter(document("getOrders", preprocessResponse(prettyPrint()), documentGetOrders()))
                .get("/orders/1");

        assertThat(response.getStatusCode()).isEqualTo(200);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("totalPrice")).isInstanceOf(Double.class);
    }

    @Test
    public void getOrdersProjection() {
        Response response = given(this.spec)
                .filter(document("getOrdersProjection", preprocessResponse(prettyPrint()), documentGetOrdersProjection()))
                .get("/orders/1?projection=orderDetail");

        assertThat(response.getStatusCode()).isEqualTo(200);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("totalPrice")).isInstanceOf(Double.class);
        assertThat((Object) parsedJson.read("user.fullName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("user.email")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("items.[0].item.itemName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("items.[0].item.description")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("items.[0].item.price")).isInstanceOf(Double.class);
        assertThat((Object) parsedJson.read("items.[0].quantity")).isInstanceOf(Integer.class);
    }

    @Test
    public void deleteOrder() {
        Response response = given(this.spec)
                .filter(document("deleteOrders", preprocessResponse(prettyPrint())))
                .delete("/orders/1");

        assertThat(response.getStatusCode()).isEqualTo(204);
    }
}
