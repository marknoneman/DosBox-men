package bddTests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/test/java"},
    plugin = {"pretty", "html:target/cucumber-html-report", "junit:target/cucumber-junit-report/allcukes.xml"},
    tags = {"@Runme"})
public class CukeRunnerTest {
}
