Feature: General utilities used for DosBOX
In order to have a robust operating system
as an end user of the system
I want to be able to use standard utilities of dos

//@Runme
Scenario: Help displays information
Given I have a standard directory setup
When I type "help"
Then I will see "help" listed

