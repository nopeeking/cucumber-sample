package sharedState;

import io.restassured.response.Response;

import java.util.Map;

public class SharedContext {
    public int bookingId;
    public Response response;
    public Map<String, Object> requestMap;
}
