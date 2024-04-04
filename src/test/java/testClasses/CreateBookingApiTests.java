package testClasses;

import api.CreateBookingApi;
import io.cucumber.java.it.Ma;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.BookingDates;
import pojo.CreateBookingRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateBookingApiTests {

    @Test(description = "Create a booking and check status code")
    public void createBookingStatusCodeValidation() {
        CreateBookingApi createBookingApi = new CreateBookingApi();
        Map<String, Object> requestBody = createBookingApi.getRequest();
        createBookingApi.setBody((requestBody));
        Response createBookingResponse = createBookingApi.sendRequest();
        createBookingResponse.then().statusCode(200)
                .and().body("bookingid", Matchers.notNullValue())
                .and().body("booking", Matchers.notNullValue());
    }

    @DataProvider(parallel = true)
    public Object[][] bookingDetailsDp() {
        return new Object[][] {
                {"Jerry", "Harper","Mineral water", true, 1000, "2024-04-01", "2024-05-01"},
                {"Jerry", "Tim","Mineral water", false, 777, "2024-06-01", "2024-07-01"},
                {"Kim", "Robert","Mineral water", true, 666, "2024-08-01", "2024-09-01"},
                {"Simon", "Peter","Mineral water", true, 200, "2024-10-01", "2024-11-01"}
        };
    }

    @Test(description = "Create a booking and check status code with help of data provider", dataProvider = "bookingDetailsDp")
    public void createBookingStatusCodeValidationWithDp(String firstname, String lastname, String additionalneeds,
                                                  boolean depositpaid, int totalprice, String checkin, String checkout) {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);
        requestBody.put("firstname", firstname);
        requestBody.put("lastname", lastname);
        requestBody.put("totalprice", totalprice);
        requestBody.put("depositpaid", depositpaid);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", additionalneeds);
        CreateBookingApi createBookingApi = new CreateBookingApi();
        createBookingApi.setBody((requestBody));
        Response createBookingResponse = createBookingApi.sendRequest();
        createBookingResponse.then().statusCode(200)
                .and().body("bookingid", Matchers.notNullValue())
                .and().body("booking", Matchers.notNullValue())
                .and().body("booking.firstname", Matchers.equalTo(firstname))
                .and().body("booking.lastname", Matchers.equalTo(lastname))
                .and().body("booking.additionalneeds", Matchers.equalTo(additionalneeds))
                .and().body("booking.depositpaid", Matchers.equalTo(depositpaid))
                .and().body("booking.totalprice", Matchers.equalTo(totalprice))
                .and().body("booking.bookingdates.checkin", Matchers.equalTo(checkin))
                .and().body("booking.bookingdates.checkout", Matchers.equalTo(checkout));

        String firstName = createBookingResponse.then().extract().jsonPath().getString("booking.firstname");
        System.out.println("Firstname: " + firstName);

        String lastName = createBookingResponse.then().extract().jsonPath().getString("booking.lastname");
        System.out.println("Lastname: " + lastName);

        String additionalNeeds = createBookingResponse.then().extract().jsonPath().getString("booking.additionalneeds");
        System.out.println("Additional needs: " + additionalNeeds);

        String depositPaid = createBookingResponse.then().extract().jsonPath().getString("booking.depositpaid");
        System.out.println("Deposit paid: " + depositPaid);

        String totalPrice = createBookingResponse.then().extract().jsonPath().getString("booking.totalprice");
        System.out.println("Total price: " + totalPrice);

        String bookingDatesFromResponse = createBookingResponse.then().extract().jsonPath().getString("booking.bookingdates");
        System.out.println("Booking dates: " + bookingDatesFromResponse);

    }

    private Map<String, Object> getDefaultRequestMap() {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> bookingDates1 = new HashMap<>();
        bookingDates1.put("checkin", "2024-06-01");
        bookingDates1.put("checkout", "2025-01-01");
        requestBody.put("firstname", "Jim");
        requestBody.put("lastname", "Harper");
        requestBody.put("totalprice", 1000);
        requestBody.put("depositpaid", true);
        requestBody.put("bookingdates", bookingDates1);
        requestBody.put("additionalneeds", "Lunch");
        return requestBody;
    }

    private CreateBookingRequest getRequestPojo() {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        createBookingRequest.setFirstname("Tim");
        createBookingRequest.setLastname("Harper");
        createBookingRequest.setTotalprice(999);
        createBookingRequest.setDepositpaid(false);
        createBookingRequest.setAdditionalneeds("Mineral water");
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2024-01-01");
        bookingDates.setCheckout("2024-06-01");
        createBookingRequest.setBookingdates(bookingDates);
        return createBookingRequest;
    }
}
