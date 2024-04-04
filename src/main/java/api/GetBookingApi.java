package api;

import http.BaseApi;
import io.restassured.http.Method;

public class GetBookingApi extends BaseApi {

    public GetBookingApi(Object bookingId) {
        super("/booking/{id}", Method.GET);
        super.setPathParams("id", bookingId);
        super.logResponse();
    }
}
