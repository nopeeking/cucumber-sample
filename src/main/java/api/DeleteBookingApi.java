package api;

import http.BaseApi;
import io.restassured.http.Method;

public class DeleteBookingApi extends BaseApi {
    public DeleteBookingApi(Object bookingId) {
        super("/booking/{id}", Method.DELETE);
        super.setPathParams("id", bookingId);
        super.logResponse();
    }
}
