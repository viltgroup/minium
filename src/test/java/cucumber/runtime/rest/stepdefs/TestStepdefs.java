package cucumber.runtime.rest.stepdefs;

import java.util.List;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class TestStepdefs {

    public static enum Gender {
        MALE, FEMALE
    }

    public static class Person {
        public String name;
        public Gender gender;
    }

    @Before
    public void before() {
        System.out.printf("# Before\n");
    }

    @After
    public void after() {
        System.out.printf("# After\n");
    }

    @When("^I say hello to (.+?)$")
    public void say_hello(String name) {
        System.out.printf("Hello, %s\n", name);
    }

    @When("^I say hello to:$")
    public void say_hello(List<Person> people) {
        for (Person person : people) {
            System.out.printf("Hello, %s %s\n", person.gender == Gender.MALE ? "Mr." : "Mrs.", person.name);
        }
    }
}
