Author: Antti Herala

The software contains 5 commands:
* .login
	* Logs in the user with the data given to the constructor
* .names
	* Prints out the list of users the user has
* .update
	* Gets a new list of users from the server (includes new logins and logoffs)
* .quit
	* Quits the program
* .connect
	* Connects user to another user
		
### How to use: ###
Server does not have any predefined users, so there must be 
at least two clients connected to work.

Start up Server.java for server and Client.java and MockClient.java:
	they create users "Leevi" and "Vertti" 

For both clients:
	Use .login to set up the workspace and see users online/offline.
	If you start up user "Leevi" first and the "Vertti", remember to use .update on "Leevi"
	before trying to send message

After both clients have been logged in and their user lists are updated:
	Use .connect on either client
	Type in the other user's name
	Type in the message
	Done.
	Other client should see the message in form of "Message from ...: ..."

If the user is the only one online, the software is useless. 
So remember to connect another one.

After this you can log the other user out by using .quit. 
Then update the list of the other user and use .names to see 
that the other user has gone offline.
