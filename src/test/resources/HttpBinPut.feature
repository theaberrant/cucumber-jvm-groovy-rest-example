Feature: HTTP PUT Request
  In order to update a resource
  Clients must call put

  Scenario: Update a resource
    When I call put
    Then the response should be successful