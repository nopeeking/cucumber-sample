package api;

import http.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

import java.util.Map;
import java.util.HashMap;

public class CreateBookingApi extends BaseApi {

    public CreateBookingApi() {
        super("/booking", Method.POST);
        super.setContentType(ContentType.JSON);
        super.logResponse();
    }

    public Map<String, Object> getRequest() {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin","2024-06-01");
        bookingDates.put("checkout","2025-01-01");
        requestBody.put("firstname", "Jim");
        requestBody.put("lastname", "Harper");
        requestBody.put("totalprice", 1000);
        requestBody.put("depositpaid", true);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Lunch");
        return requestBody;
    }
}
