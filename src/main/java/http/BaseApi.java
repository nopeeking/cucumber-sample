package http;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.Method;
import utils.PropertyConfiguration;


public abstract class BaseApi {

    private final RequestSpecification requestSpecification;
    private final Method method;
    public BaseApi(String basePath, Method method) {
        this.requestSpecification = RestAssured
                .given()
                .and().filter(new RequestLoggingFilter())
                .and().baseUri(PropertyConfiguration.getConfig().baseUri())
                .and().basePath(basePath);
        this.method = method;
    }

    public void setBody(Object body) {
        this.requestSpecification.body(body);
    }
    public void setHeader(String headerName, String value) {
        this.requestSpecification.header(headerName, value);
    }
    public void setContentType(ContentType contentType) {
        this.requestSpecification.contentType(contentType);
    }
    public void setPathParams(String paramName, Object value, Object...pathParamValuePairs) {
        this.requestSpecification.pathParams(paramName, value, pathParamValuePairs);
    }
    public void logResponse() {
        this.requestSpecification.filter(new ResponseLoggingFilter());
    }

    public Response sendRequest() {
        return switch (method) {
            case GET -> this.requestSpecification.when().get();
            case POST -> this.requestSpecification.when().post();
            case PUT -> this.requestSpecification.when().put();
            case PATCH -> this.requestSpecification.when().patch();
            case DELETE -> this.requestSpecification.when().delete();
            default -> throw new IllegalStateException("Unexpected value: " + method);
        };
    }
}
