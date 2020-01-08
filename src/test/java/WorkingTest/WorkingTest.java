package WorkingTest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class WorkingTest {

    private String input;

    @Given("the input {string}")
    public void theInput(String inputString) {
        this.input = inputString;

        assertThat(this.input, is(equalTo(inputString)));
    }

    @Then("the result should be {string}")
    public void theResultShouldBe(String res) {
        assertThat(this.input, is(equalTo(res)));
    }
}
