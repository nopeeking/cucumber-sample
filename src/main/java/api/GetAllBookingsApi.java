package api;

import http.BaseApi;
import io.restassured.http.Method;

public class GetAllBookingsApi extends BaseApi {

    public GetAllBookingsApi() {
        super("/booking", Method.GET);
    }

}
