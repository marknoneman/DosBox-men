Feature: Manipulate directories in DosBOX
In order to have a usable operating system
as an end user of the system
I want to be able to modify directories

@Runme
Scenario: List an empty directory
Given I have an empty directory 
When I type "dir"
Then I will see nothing listed

@Runme
Scenario: List subdirectories
Given I have a standard directory setup
When I type "dir"
Then I will see "subDir1" listed
And "subDir1" is a type of "directory"

Scenario: Make a subdirectory
Given I have a standard directory setup
When I type "mkdir smirnoff"
And I type "dir"
Then I will see "smirnoff" listed
And "smirnoff" is a type of "directory"

Scenario: Remove a subdirectory
Given I have a standard directory setup
When I type "rmdir subDir1"
And I type "dir"
Then I will not see "subDir1" listed

Scenario: Cannot remove a full subdirectory
Given I have a standard directory setup
When I type "rm subDir1"
Then I will see "error" listed

