package step_definitions;

import api.CreateBookingApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import sharedState.SharedContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateBookingApiStepDefs {
    private Map<String, Object> requestBody;
    private Response createBookingResponse;
    private SharedContext sharedContext;

    public CreateBookingApiStepDefs(SharedContext sharedContext) {
        this.sharedContext = sharedContext;
    }

    @Given("we have a booking request")
    public void we_have_a_booking_request(List<Map<String, Object>> bookingRequestList) {
        Map<String,Object> requestMap = bookingRequestList.getFirst();
        requestBody = new HashMap<>();
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", requestMap.get("checkin"));
        bookingDates.put("checkout", requestMap.get("checkout"));
        requestBody.put("firstname", requestMap.get("firstname"));
        requestBody.put("lastname", requestMap.get("lastname"));
        requestBody.put("totalprice", requestMap.get("totalprice"));
        requestBody.put("depositpaid", requestMap.get("depositpaid"));
        requestBody.put("additionalneeds", requestMap.get("additionalneeds"));
        requestBody.put("bookingdates", bookingDates);
        this.sharedContext.requestMap = requestBody;
    }

    @When("we send the request to create booking api")
    public void we_send_the_request_to_create_booking_api() {
        CreateBookingApi createBookingApi = new CreateBookingApi();
        createBookingApi.setBody(requestBody);
        createBookingResponse = createBookingApi.sendRequest();
        this.sharedContext.response = createBookingResponse;
    }

    @Then("HTTP response status code should be {int}")
    public void http_response_status_code_should_be(int statusCode) {
        this.sharedContext.response.then().statusCode(statusCode);
    }

    @Then("HTTP response should contain an error")
    public void http_response_should_contain_an_error() {
        String response = createBookingResponse.then().statusCode(500).and().extract().asString();
        Assert.assertTrue(response.equalsIgnoreCase("Internal server error"));
    }

    @When("we store the booking from create response")
    public void we_store_the_booking_from_create_response() {
        this.sharedContext.bookingId = createBookingResponse.then().extract().jsonPath().getInt("bookingid");
    }

    @And("validate that response has bookingid")
    public void validate_that_response_has_bookingid() {
        this.sharedContext.response
                .then().body("bookingid", Matchers.notNullValue())
                .and().body("booking", Matchers.notNullValue());
    }
}
