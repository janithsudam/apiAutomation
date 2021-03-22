import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class UserTest {

    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @BeforeClass
    public void before(){
        requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri("https://gorest.co.in/public-api/users")
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    @Test
    public void testGetAllUsers(){
        ValidatableResponse response = given(requestSpecification)
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .log().all();
        Assert.assertEquals(response.extract().body().jsonPath().get("meta.pagination.pages").toString(),"100");
    }

    @Test
    public void testCreateUser(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "aajTerrill.Morar@mailinator.com");
        jsonObject.put("name", "Tester_AKQWDERTIQQAGTRRI");
        jsonObject.put("gender", "Male");
        jsonObject.put("status", "Active");

        ValidatableResponse response = given(requestSpecification)
                .header(new Header("Authorization", "Bearer 5aa8220420419fd5890bb88a1767ed7cb1abc4412024bfff91513a40d6e19823"))
                .body(jsonObject.toJSONString())
                .when()
                .post()
                .then()
                .spec(responseSpecification)
                .log().all();
        Assert.assertEquals(response.extract().body().jsonPath().get("data.name"),"Tester_AKQWDERTIQQAGTRRI");
    }

    @Test
    public void testEditUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "osa2@google.com");
        jsonObject.put("name", "Osanda");
        jsonObject.put("gender", "Male");
        jsonObject.put("status", "Active");

        ValidatableResponse response = given(requestSpecification)
                .header(new Header("Authorization", "Bearer 5aa8220420419fd5890bb88a1767ed7cb1abc4412024bfff91513a40d6e19823"))
                .body(jsonObject.toJSONString())
                .when()
                .put("/1577")
                .then()
                .spec(responseSpecification)
                .log().all();
        Assert.assertEquals(response.extract().body().jsonPath().get("data.name"), "Osanda");
    }

    @Test
    public void testDeleteUser(){
        ValidatableResponse response = given(requestSpecification)
                .header(new Header("Authorization", "Bearer 5aa8220420419fd5890bb88a1767ed7cb1abc4412024bfff91513a40d6e19823"))
                .when()
                .delete("/1576")
                .then()
                .spec(responseSpecification)
                .log().all();
    }
}
