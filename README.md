# Hike Tracker

This application allows for users to track their **hikes** while rating them 
and being able to see their **accomplishments** such as total elevation climbed and
distance.
This is intended for users who like to hike frequently and would like to
track their hikes *easily* and see a list of their accomplishments.

I am interested in this project because I enjoy hiking and have been
keeping track in a journal, but now I have been inspired to create an application
that has that functionality while giving me new statistics and the ability to 
view my hikes easily.

## User Stories

- As a user, I want to be able to add hikes to my accomplishments list
- As a user, I want to be able to delete hikes from my accomplishments list
- As a user, I want to be able to view the hikes from my accomplishments list
- As a user, I want to be able to select a hike and edit a statistic, such as rating out of 10
- As a user, I want to be able to sort my hikes by length or rating
- As a user, I want to be able to have the option to save my hikes
- As a user, I want to be able to have the option to load my hikes from a file
 
# Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a 
Y" by clicking the add tab and inputting valid information into all the text fields,
then clicking the "Add" button to add a hike to the Hike List.
- You can generate the second required action related to the user story "adding multiple Xs to a
Y" by clicking the remove tab, and inputting a valid index number into the text field, then clicking 
the remove button, in order to remove the hike at that index number from the Hike List
- You can view all the hikes by clicking the "Hikes" tab
- You can locate my visual component after successfully adding a hike to the hike list. This image will
persist until the program is closed.
- You can save the state of my application by going to the save/load tab and clicking the "Save to file" button. 
This will save the hike list data into a json file.
- You can reload the state of my application by going to the save/load tab and clicking the "Load from file" button. 
- This will load a hike list into the application from a json file, if the file exists.

# Phase 4: Task 2
Sat Apr 06 22:53:30 PDT 2024

New Hike added to HikeList

Sat Apr 06 22:53:43 PDT 2024

New Hike added to HikeList

Sat Apr 06 22:53:48 PDT 2024

New Hike added to HikeList

Sat Apr 06 22:53:51 PDT 2024

HikeList sorted by Length

Sat Apr 06 22:53:52 PDT 2024

HikeList sorted alphabetically by Name

Sat Apr 06 22:53:53 PDT 2024

HikeList sorted by Rating

Sat Apr 06 22:53:58 PDT 2024

Hike at 1 removed

Sat Apr 06 22:54:05 PDT 2024

New Hike added to HikeList

Sat Apr 06 22:54:05 PDT 2024

New Hike added to HikeList

Sat Apr 06 22:54:15 PDT 2024

HikeList sorted by Rating

# Phase 4: Task 3
When reviewing my code, I think my HikeApp class lacks cohesion, and 
it would be beneficial to split off its responsibilities into multiple classes.
For example, the creating of the panel could be split off into one class, and 
the actual calls of HikeList could be in another class to better follow
the single responsibility principle. If I had more time I would try to follow this principle better.