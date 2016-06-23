package bddTests.features.step_definitions;
import bddTests.automationTests.DosBoxTest;
import cucumber.api.java.en.*;

public class StepDefs {
	private DosBoxTest dosBoxTest;

@Given("^I have a standard directory setup$")
public void i_have_a_standard_directory_setup() throws Throwable {
	dosBoxTest = new DosBoxTest();
	dosBoxTest.createStandardDirectory();
}

@Given("^I have an empty directory$")
public void i_have_an_empty_directory() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
	dosBoxTest = new DosBoxTest();
	dosBoxTest.createRootDirectory();
}

@When("^I type \"(.*?)\"$")
public void i_type(String arg1) throws Throwable {
	dosBoxTest.testACommand(arg1);
}


@Then("^I will see \"(.*?)\" listed$")
public void i_will_see_listed(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    dosBoxTest.checkFileOrDirectoryExists(arg1);
}


@Then("^I will see nothing listed$")
public void i_will_see_nothing_listed() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
	dosBoxTest.CheckDirectoryIsEmpty();
}

@Then("^\"(.*?)\" is a type of \"(.*?)\"$")
public void is_a_type_of(String arg1, String arg2) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
	dosBoxTest.checkType(arg1, arg2);
}

@Then("^I will not see \"(.*?)\" listed$")
public void i_will_not_see_listed(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    dosBoxTest.checkFileOrDirectoryDoesnotExist(arg1);
}


}
