package api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class UserObject {
    private JsonPath jsonPath;
    private long maleCount;
    private long femaleCount;

    public UserObject(Response response){
        this.jsonPath = new JsonPath(response.getBody().asString());
        this.maleCount = getGenderList().stream().filter(g -> g.equalsIgnoreCase("male")).count();
        this.femaleCount = getGenderList().stream().filter(g -> g.equalsIgnoreCase("female")).count();
    }

    private List<String> getGenderList() {
        return this.jsonPath.getList("gender");
    }

    public long getMaleCount(){
        return this.maleCount;

    }
    public long getFemaleCount(){
        return this.femaleCount;
    }
    public long getDifference(){
        return Math.abs(getMaleCount() + (-getFemaleCount()));
    }

}
