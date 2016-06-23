Feature: Manipulate files in DosBOX
In order to have a usable operating system
as an end user of the system
I want to be able to modify files

Scenario: List files
Given I have a standard directory setup
When I type "cd SubDir1"
And I type "dir"
Then I will see "file1InSubDir1" listed
And "file1InSubDir1" is a type of "file"

Scenario: Create file
Given I have a standard directory setup
When I type "mkfile smirnoff"
And I type "dir"
Then I will see "smirnoff" listed
And "File1" is a type of "file"

Scenario: Delete file
Given I have a standard directory setup
When I type "del" "filename" 
And I type "dir" 
Then I will not see "filename" listed
