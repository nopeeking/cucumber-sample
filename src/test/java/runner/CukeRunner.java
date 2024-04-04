package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"step_definitions", "hooks"},
        tags = "@crud",
        //read cucumber documentation to view more plugin options for reporting
        plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber.json"}
)
public class CukeRunner extends AbstractTestNGCucumberTests {
}
