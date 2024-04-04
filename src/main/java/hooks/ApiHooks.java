package hooks;

import io.cucumber.java.*;

public class ApiHooks {

    @Before("@crud")
    public void beforeTest() {
        System.out.println("example crud test case starts...");
    }

    @After("@crud")
    public void afterTest() {
        System.out.println("example crud test case ends...");
    }
}
