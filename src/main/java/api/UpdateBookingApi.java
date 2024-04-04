package api;

import http.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

import java.util.HashMap;
import java.util.Map;

public class UpdateBookingApi extends BaseApi {
    public UpdateBookingApi(Object bookingId) {
        super("/booking/{id}", Method.PUT);
        super.setPathParams("id", bookingId);
        super.setContentType(ContentType.JSON);
        super.logResponse();
    }

    public Map<String, Object> getRequest() {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin","2024-12-01");
        bookingDates.put("checkout","2025-01-01");
        requestBody.put("firstname", "Imposter");
        requestBody.put("lastname", "Kim");
        requestBody.put("totalprice", 990);
        requestBody.put("depositpaid", false);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Lunch");
        return requestBody;
    }
}
