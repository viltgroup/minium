Feature: Test 1
  Scenario Outline: Hello world
    Given <foo> exists
    Then <bar> should occur
    Examples:
    #@source :src/test/resources/file-that-dont-exists.csv
    |foo2 |bar2 |
    |val1 |val2 |