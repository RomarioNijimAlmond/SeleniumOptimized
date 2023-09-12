package apitests;

import api.ApiFunctions;
import api.GoRestApi;
import api.UserObject;
import com.squareup.okhttp.OkHttpClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.hc.core5.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.json.JSONArray;
import utils.JsonUtils;
import utils.Utils;

import static io.restassured.RestAssured.given;

public class GoRestCrudTests extends BaseTestApi {
    OkHttpClient client;
    private String baseUrl = "https://gorest.co.in/public/v2";

    @BeforeMethod
    public void initilizeObject() {
        client = new OkHttpClient();
    }



    /**
     * takes the difference between the male and female - if one of the gender count is more than the other than post relevant gender by the difference number to get even
     */
    @Test(description = "retrieve all genders and count them and make male and female count even")
    public void tc01_getUsersAllGendersAndCreateUsersWithSpecificGender() throws InterruptedException {
        Response response = GoRestApi.getUsers();
        UserObject userObject = new UserObject(response);

        if(userObject.getDifference() == 0){
            assert true;
            return;
        }

        if (userObject.getMaleCount() > userObject.getFemaleCount()) {
            for (int i = 0; i <= userObject.getDifference(); i++) {
                response = GoRestApi.postUser(JsonUtils.getRandomUserObject(false));
                Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
            }
        } else {
            for (int i = 0; i <= userObject.getDifference(); i++) {
                response = GoRestApi.postUser(JsonUtils.getRandomUserObject(true));
                Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
            }
        }
        response = GoRestApi.getUsers();
        UserObject updatedUserObject = new UserObject(response);
        Assert.assertEquals(updatedUserObject.getDifference(), 0);

    }

    @Test(description = "delete all users that have inactive status")
    public void tc03_deleteInactiveUsers() throws InterruptedException {
        Response response = given()
                .header("Authorization", "Bearer " + Utils.readProperty("apiToken"))
                .log()
                .all()
                .when()
                .get(baseUrl + "/users")
                .then().log().all()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        JSONArray users = new JSONArray(response.asString());

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if ("inactive".equals(user.getString("status"))) {
                int userId = user.getInt("id");
                given()
                        .when()
                        .log().all()
                        .delete(baseUrl + "/users/" + userId)
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(HttpStatus.SC_OK);
            }
        }
    }

    @Test(description = "modify all of the users email and modify their email extension to '.co.il' then assert all extensions changed to .co.il")
    public void tc04_updateUserEmailExtension() throws InterruptedException {
        Response response = given()
                .log()
                .all()
                .when()
                .get(baseUrl + "/users")
                .then().log().all()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        JSONArray users = new JSONArray(response.asString());
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String originalMail = user.getString("email");
            String updatedEmail = originalMail.replaceFirst("\\.(.*?)\\.", ".co.il");
            int userId = user.getInt("id");
            JSONObject updateData = new JSONObject();
            updateData.put("email", updatedEmail);
            given()
                    .header("Authorization", "Bearer " + Utils.readProperty("apiToken"))
                    .log()
                    .all()
                    .contentType(ContentType.JSON)
                    .when()
                    .body(updateData)
                    .put(baseUrl + "/users/" + userId)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK);

//---------------------------------------------------------
            ApiFunctions.get(baseUrl + "/users" + userId, ContentType.JSON);
            String updatedEmailFromServer = response.jsonPath().getString("email");
            Assert.assertTrue(updatedEmailFromServer.endsWith(".co.il"), "Email extension is not '.co.il'");
        }
    }
}


