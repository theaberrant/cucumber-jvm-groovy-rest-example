Feature: HTTP POST Request
  In order to create a resource
  Clients must call post

  Scenario: Create a resource
    When I call post
    Then the response should be successful