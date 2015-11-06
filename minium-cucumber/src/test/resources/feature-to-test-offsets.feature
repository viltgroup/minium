# language: en
# ------------------------------------------------------------------------------
Feature: Send Emails

  Scenario: Send an Email
    When I click on button "Compose"
    And I fill:
      # @source:data-table.csv
      | Recipients | Rui Figueira   |
      | Subject    | Minium Test    |
      | Message    | My new Message |
      | Message    | My new Message |
      | Message    | My new Message |
    And I click on button "Send"
    And I navigate to section "Sent"
    Then I should see an email with:
      | Recipients | Rui Figueira   |
      | Subject    | Minium Test    |
      | Message    | My new Message |
   
  
  Scenario: Send an Email
    When I click on button "Compose"
    And I fill:
      | Recipients | Rui Figueira   |
      | Subject    | Minium Test    |
      | Message    | My new Message |
    And I click on button "Send"
    And I navigate to section "Sent"
    Then I should see an email with:
      | Recipients | Rui Figueira   |
      | Subject    | Minium Test    |
      | Message    | My new Message |


   