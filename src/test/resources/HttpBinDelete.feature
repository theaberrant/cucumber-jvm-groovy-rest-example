Feature: HTTP Delete Request
  In order to remove a resource
  Clients must call delete

  Scenario: Delete a resource
    When I call delete
    Then the response should be successful
