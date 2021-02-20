Feature: Access News Portal

  In order to be able to access Yahoo's news portal
  As a user of the Yahoo News Webpage
  I want to be able to reach World, US, Health, Science, Politics sections, and search for stories

  Scenario Outline: Reachability of sections
    Given I am a user of news.yahoo.com
    When I visit news.yahoo.com
    And I click on the "<str1>" section
    Then I should be taken to "<str1>" section
    And the section should have at least <res> stories
    Examples:
      | str1 | res |
      | US | 7 |
      | Health | 7 |
      | Science | 7 |
      | Politics | 7 |
      | World | 7 |

  Scenario: Search functionality
    Given I am a user of news.yahoo.com
    When I visit news.yahoo.com
    And I search for stories about "Donald Trump"
    Then I should see the search results of "Donald Trump"
    And there should be at least 5 stories in the search results
    When I click on the first story in the results
    Then I should be taken to the story

  Scenario: Weather Widget view
    Given I am a user of news.yahoo.com
    When I visit news.yahoo.com
    Then I should see the widget

  Scenario: Weather Widget Search City functionality
    Given I am a user of news.yahoo.com
    When I visit news.yahoo.com
    Then I should see the widget
    When I try to get weather about "Madrid"
    Then I should see weather forecast of "Madrid"

  Scenario: Weather Widget Search City functionality 2
    Given I am a user of news.yahoo.com
    When I visit news.yahoo.com
    Then I should see the widget
    When I try to click on the widget
    Then I should be redirected to a weather forecast webpage


