Feature: HTTP GET Request
  In order to retrieve a resource
  Clients must call get

  Scenario: Retrieve a resource
    When I call get
    Then the response should be successful