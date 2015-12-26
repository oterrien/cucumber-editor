Feature: Scenario View Management
  In order to test the scenario view
  As a user
  I want to create new scenario and update data

# Standard scenario
  Scenario: define a standard and reusable scenario
    Given a standard scenario
    Then the scenario should be:
      """
      Scenario: Wilson posts to his own blog
      given a global administrator named "Greg"
      and a customer named "Wilson"
      and a blog named "Expensive Therapy" owned by "Wilson"
      when I open the blog
      | param1 | param2 |
      | value11 | value12 |
      | value21 | value22 |
      then the blog is called
      """

  Scenario: use the view to load the scenario
    Given a standard scenario
    When I load the scenario
    Then the scenario's title should be "Wilson posts to his own blog"
    And the scenario's steps should be:
      """
      given a global administrator named "Greg"
      and a customer named "Wilson"
      and a blog named "Expensive Therapy" owned by "Wilson"
      when I open the blog
      | param1 | param2 |
      | value11 | value12 |
      | value21 | value22 |
      then the blog is called
      """

  Scenario: use the view to update the scenario's title
    Given a standard scenario
    When I load the scenario
    And I update the scenario's title to "Wilson posts to his own blog UPDATED"
    And I validate the scenario update
    Then the scenario's title should be "Wilson posts to his own blog UPDATED"
    And the scenario's steps should be:
      """
      given a global administrator named "Greg"
      and a customer named "Wilson"
      and a blog named "Expensive Therapy" owned by "Wilson"
      when I open the blog
      | param1  | param2  |
      | value11 | value12 |
      | value21 | value22 |
      then the blog is called
      """

  Scenario: use the view to update the scenario's steps
    Given a standard scenario
    When I load the scenario
    And I update the scenario's steps to:
      """
      given a global administrator named "Toto"
      and a customer named "Wilson"
      and a blog named "UPDATE" owned by "Wilson"
      when I open the blog
      | param1 | param2 | param3 |
      | value11 | value12 | value13 |
      | value21 | value22 | value23 |
      then the blog is called
      """
    And I validate the scenario update
    Then the scenario's title should be "Wilson posts to his own blog"
    And the scenario's steps should be:
      """
      given a global administrator named "Toto"
      and a customer named "Wilson"
      and a blog named "UPDATE" owned by "Wilson"
      when I open the blog
      | param1  | param2  | param3  |
      | value11 | value12 | value13 |
      | value21 | value22 | value23 |
      then the blog is called
      """
