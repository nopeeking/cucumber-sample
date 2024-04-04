package testClasses;

import api.CreateBookingApi;
import api.DeleteBookingApi;
import api.GetBookingApi;
import api.UpdateBookingApi;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CrudTest {

    private int bookingId;

    @Test(description = "create booking")
    public void createBooking() {
        CreateBookingApi createBookingApi = new CreateBookingApi();
        Map<String, Object> requestBody = createBookingApi.getRequest();
        createBookingApi.setBody(requestBody);
        Response createBookingResponse = createBookingApi.sendRequest();
        bookingId = createBookingResponse.then().statusCode(200)
                .and().body("bookingid", Matchers.notNullValue())
                .and().body("booking", Matchers.notNullValue())
                .extract().jsonPath().getInt("bookingid");

    }

    @Test(description = "retrieve booking", dependsOnMethods = "createBooking")
    public void retrieveBooking() {
        GetBookingApi getBookingApi = new GetBookingApi(this.bookingId);
        Response retrieveBookingResponse = getBookingApi.sendRequest();
        retrieveBookingResponse.then().statusCode(200)
                .and().body("bookingdates", Matchers.notNullValue());
    }

    @Test(description = "update booking", dependsOnMethods = "retrieveBooking")
    public void updateBooking() {
        UpdateBookingApi updateBookingApi = new UpdateBookingApi(this.bookingId);
        Map<String, Object> requestBody = updateBookingApi.getRequest();
        requestBody.replace("totalprice", 499);
        requestBody.replace("depositpaid", true);
        updateBookingApi.setHeader("Cookie", "token=" + this.getToken());
        updateBookingApi.setBody(requestBody);
        updateBookingApi.sendRequest().then().statusCode(200)
                .and().body("totalprice", Matchers.equalTo(499))
                .and().body("depositpaid", Matchers.equalTo(true));
    }

    @Test(description = "delete booking", dependsOnMethods = "updateBooking")
    public void deleteBooking() {
        DeleteBookingApi deleteBookingApi = new DeleteBookingApi(this.bookingId);
        deleteBookingApi.setHeader("Cookie", "token=" + getToken());
        deleteBookingApi.sendRequest().then().statusCode(201);
    }

    private String getToken() {

        Map<String, String> request = new HashMap<>();
        request.put("username", "admin");
        request.put("password", "password123");

        RequestSpecification requestSpecification = RestAssured.given();
        Response response = requestSpecification.contentType(ContentType.JSON)
                .and().filters(new ResponseLoggingFilter(), new RequestLoggingFilter())
                .and().baseUri("https://restful-booker.herokuapp.com")
                .and().basePath("/auth")
                .and().body(request)
                .when().post();
        String token = response
                .then().statusCode(200)
                .and().extract().jsonPath().getString("token");

        return token;
    }
}
