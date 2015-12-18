Feature: Feature View Management
  In order to test the feature view
  As a user
  I want to create new feature and update its data

# Standard scenario
  Scenario: define a standard and reusable feature
    Given a standard feature
    Then the feature is:
"""
Feature: Feature View Management
In order to test the feature view
As a user
I want to create new feature and update data
"""

  Scenario: use the view to load the feature
    Given a standard feature
    When I load the feature into the view
    Then the feature's title from view should be "Feature View Management"
    And the feature's description from view should be:
"""
In order to test the feature view
As a user
I want to create new feature and update data
"""

  Scenario: use the view to update the feature's title
    Given a standard feature
    When I load the feature into the view
    And I update the feature's title from view to "Feature View Management UPDATED"
    Then the feature's title from view should be "Feature View Management UPDATED"
    And the feature's description from view should be:
"""
In order to test the feature view
As a user
I want to create new feature and update data
"""

  Scenario: use the view to update the feature's description
    Given a standard feature
    When I load the feature into the view
    And I update the feature's description from view to:
"""
In order to update the feature's description
As a tester
I want to update feature's description
"""
    Then the feature's title from view should be "Feature View Management"
    And the feature's description from view should be:
"""
In order to update the feature's description
As a tester
I want to update feature's description
"""
