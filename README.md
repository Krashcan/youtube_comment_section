**Youtube Comment Section**

I have tried to implement youtube comment system's design. Following actions are supported

* Adding and removing a comment from comment section

* Editing existing comment in a comment section

* Upvoting, downvoting and reporting a comment.

* Reporting a user

* Replying to a comment

* Sorting comments based on time or upvotes

There are three major classes in this system - 

* CommentSection - It is responsible for storing all the comments and displaying them in a particular order. 

* UserComment - It is responsible for maintaining information regarding author of comment and contents of comment. It 
also tracks whether a comment should be hidden due to too many reports, and maintains a count of upvotes and downvotes.

* User - It is responsible for storing information with respect to the user, and how many times he has been reported.

