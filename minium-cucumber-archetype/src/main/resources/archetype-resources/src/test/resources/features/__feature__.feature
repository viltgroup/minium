# language: en
# ------------------------------------------------------------------------------
Feature: Search results in Google

  Scenario: Search something in google
    Given I'm at http://www.google.com/ncr
    When I search for minium github
    Then a link for https://github.com/viltgroup/minium is displayed

  Scenario Outline: Search something in google (results in a JSON file)
    Given I'm at http://www.google.com/ncr
    When I search for <search_query>
    Then links corresponding to <search_query> are displayed

    Examples: 
      | search_query  |
      | Minium Github |
      | Selenium      |
