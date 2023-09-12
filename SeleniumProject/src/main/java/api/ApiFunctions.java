package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.hc.core5.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ApiFunctions {

    public static Response get(String url, ContentType contentType) {
        Response response = given()
                .log()
                .all()
                .contentType(contentType)
                .when()
                .get(url)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
        return response;
    }

    public static Response post(String url, String body, ContentType contentType, String token) {
        Response response = given()
                .log()
                .all()
                .contentType(contentType)
                .body(body)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(url)
                .then()
                .extract().response();
        return response;
    }

    public static Response post(String url, String body, ContentType contentType) {
        Response response = given()
                .log()
                .all()
                .contentType(contentType)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();
        return response;
    }

    public static Response put(String url, String body, ContentType contentType, String token) {
        Response response = given()
                .log()
                .all()
                .contentType(contentType)
                .header("Authorization", "Bearer " + token)
                .when()
                .put(body)
                .then()
                .extract().response();
        return response;
    }


    public static Response delete(String url, ContentType contentType, String token) {
        Response response = given()
                .log()
                .all()
                .contentType(contentType)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(url)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
        return response;
    }
}

