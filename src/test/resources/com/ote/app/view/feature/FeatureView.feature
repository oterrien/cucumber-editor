Feature: Feature View Management
  In order to test the feature view
  As a user
  I want to create new feature and update data

# Standard scenario
  Scenario: define a standard and reusable feature
    Given a standard feature
    Then the feature should be:
      """
      Feature: Feature View Management
      In order to test the feature view
      As a user
      I want to create new feature and update data
      """

  Scenario: use the view to load the feature
    Given a standard feature
    When I load the feature
    Then the feature's title should be "Feature View Management"
    And the feature's description should be:
      """
      In order to test the feature view
      As a user
      I want to create new feature and update data
      """

  Scenario: use the view to update the feature's title
    Given a standard feature
    When I load the feature
    And I update the feature's title to "Feature View Management UPDATED"
    And I validate the update
    Then the feature's title should be "Feature View Management UPDATED"
    And the feature's description should be:
      """
      In order to test the feature view
      As a user
      I want to create new feature and update data
      """

  Scenario: use the view to update the feature's description
    Given a standard feature
    When I load the feature
    And I update the feature's description to:
      """
      In order to update the feature's description
      As a tester
      I want to update feature's description
      """
    And I validate the update
    Then the feature's title should be "Feature View Management"
    And the feature's description should be:
      """
      In order to update the feature's description
      As a tester
      I want to update feature's description
      """
