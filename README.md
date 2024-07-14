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

When reviewing my code, I think my HikeApp class lacks cohesion, and 
it would be beneficial to split off its responsibilities into multiple classes.
For example, the creating of the panel could be split off into one class, and 
the actual calls of HikeList could be in another class to better follow
the single responsibility principle.
