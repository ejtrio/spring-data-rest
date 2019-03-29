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

public class UserRepositoryTests extends RepositoryTestBase {

    @Before
    public void loadData() throws Exception {
        given(this.spec)
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/user.json"))
                .post("/users");
    }

    @Test
    public void postUser() throws Exception {
        System.out.println(JsonHelper.getJsonStringFromFile("json/request/user.json"));

        Response response = given(this.spec)
                .filter(document("postUsers", preprocessResponse(prettyPrint()), documentPostUsers()))
                .contentType(ContentType.JSON)
                .body(JsonHelper.getJsonStringFromFile("json/request/user.json"))
                .post("/users");

        assertThat(response.getStatusCode()).isEqualTo(201);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("firstName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("lastName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("email")).isInstanceOf(String.class);
    }

    @Test
    public void getUser() {
        Response response = given(this.spec)
                .filter(document("getUsers", preprocessResponse(prettyPrint()), documentGetUsers()))
                .get("/users/1");

        assertThat(response.getStatusCode()).isEqualTo(200);

        DocumentContext parsedJson = JsonPath.parse(response.asString());
        assertThat((Object) parsedJson.read("firstName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("lastName")).isInstanceOf(String.class);
        assertThat((Object) parsedJson.read("email")).isInstanceOf(String.class);
    }

    @Test
    public void deleteUser() {
        Response response = given(this.spec)
                .filter(document("deleteUsers", preprocessResponse(prettyPrint())))
                .delete("/users/1");

        assertThat(response.getStatusCode()).isEqualTo(204);
    }
}
