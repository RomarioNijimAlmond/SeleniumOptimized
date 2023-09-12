package utils;

import com.github.javafaker.Faker;
import org.json.JSONObject;

public class JsonUtils {
    public static String getRandomUserObject(boolean isMale){
        Faker faker = new Faker();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", faker.idNumber());
        jsonObject.put("name", faker.name());
        jsonObject.put("email", faker.internet().emailAddress());
        if(isMale) {
            jsonObject.put("gender", "male");
        }else {
            jsonObject.put("gender", "female");
        }
        jsonObject.put("status", "active");
        return jsonObject.toString();
    }
}
