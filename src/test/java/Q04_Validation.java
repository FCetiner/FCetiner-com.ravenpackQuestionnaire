package get_http_request.day14;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class Q04_Validation {

//    "message": "Malformed feed request, check the documentation."

    @Test
    public void test(){
        //Setup url
        String endpoint="https://feed.ravenpack.com/1.0/json/";
        Response response=given().when().get(endpoint);
        response.prettyPrint();
        //setup expected Data
        HashMap<String,Object> expectedData=setupTestData();
        //Assertion
        //1 Matcher class
        response.then().assertThat().statusCode(404).body("message",equalTo("Malformed feed request, check the documentation."));
        //2 JsonPath
        JsonPath json=response.jsonPath();
        assertEquals(expectedData.get("message"),json.getString("message"));

        //3 De serialization
        HashMap<String,Object>actualData=response.as(HashMap.class);
        assertEquals(expectedData.get("message"),actualData.get("message"));
    }
    public HashMap<String,Object>setupTestData(){
        HashMap<String,Object>expectedData=new HashMap<>();
        expectedData.put("message","Malformed feed request, check the documentation.");
        return expectedData;
    }
}
