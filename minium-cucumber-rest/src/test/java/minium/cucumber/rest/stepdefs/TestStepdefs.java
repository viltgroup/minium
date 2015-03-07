/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.cucumber.rest.stepdefs;

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
    public void sayHello(String name) {
        System.out.printf("Hello, %s\n", name);
    }

    @When("^I say hello to:$")
    public void sayHello(List<Person> people) {
        for (Person person : people) {
            System.out.printf("Hello, %s %s\n", person.gender == Gender.MALE ? "Mr." : "Mrs.", person.name);
        }
    }
}
