Feature: Test 1
  Scenario Outline: Hello world
    Given <foo> exists
    Then <bar> should occur
    Examples:
    #@source :data-foo.csv
    |foo2 |bar2 |
    |val1 |val2 |