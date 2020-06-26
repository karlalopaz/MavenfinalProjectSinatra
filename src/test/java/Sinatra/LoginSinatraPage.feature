Feature: Sinatra Login

  Scenario: User can login to sinatra page
    Given I navigate to sinatra page
    When I login with correct credentials
    Then I can see sinatra Home Page


  Scenario: User can like a song
    Given I navigate to sinatra page
    And I login with correct credentials
    When I am in the songs page
    And I select song number 2
    Then I can click on like a song


