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

public class ItemRepositoryTests extends RepositoryTestBase {

    @Before
    public void loadData() throws Exception {
        given(this.spec)
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/item.json"))
                .post("/items");
    }

    @Test
    public void postItem() throws Exception {
        Response response = given(this.spec)
                .filter(document("postItems", preprocessResponse(prettyPrint()), documentPostItems()))
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/item.json"))
                .post("/items");

        assertThat(response.getStatusCode()).isEqualTo(201);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("itemName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("description")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("price")).isInstanceOf(Double.class);
    }

    @Test
    public void getItem() {
        Response response = given(this.spec)
                .filter(document("getItems", preprocessResponse(prettyPrint()), documentGetItems()))
                .get("/items/1");

        assertThat(response.getStatusCode()).isEqualTo(200);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("itemName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("description")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("price")).isInstanceOf(Double.class);
    }

    @Test
    public void deleteItem() {
        Response response = given(this.spec)
                .filter(document("deleteItems", preprocessResponse(prettyPrint())))
                .delete("/items/1");

        assertThat(response.getStatusCode()).isEqualTo(204);
    }
}
