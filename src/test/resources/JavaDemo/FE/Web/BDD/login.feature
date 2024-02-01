Feature: Login
  Member User can login by providing email and password

  Scenario: Login with valid credentials
    Given user is in login page
    When user input valid email and valid password
    And user click submit button
    Then user is redirected to Homepage
