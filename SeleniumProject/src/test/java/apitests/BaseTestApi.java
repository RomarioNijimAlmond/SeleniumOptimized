package apitests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeClass;

public class BaseTestApi {
    public Faker faker;

    @BeforeClass
    public void setup() {
        faker = new Faker();
        RestAssured.baseURI = "https://gorest.co.in/public/v2";
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }
}
