package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.Utils;

public class GoRestApi {
    private static String baseUrl = "https://gorest.co.in/public/v2";
    private static String usersPath = "/users";

    public static Response getUsers(){
        return ApiFunctions.get(baseUrl + usersPath, ContentType.JSON);
    }
    public static Response postUser(String data){
        return ApiFunctions.post(baseUrl + usersPath, data, ContentType.JSON, Utils.readProperty("apiToken"));
    }
}
