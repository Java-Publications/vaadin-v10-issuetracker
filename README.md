<center>
<a href="https://vaadin.com">
 <img src="https://vaadin.com/images/hero-reindeer.svg" width="200" height="200" /></a>
</center>

#Vaadin V10 - Demo application - Issuetracker

In this example we are using the database first approach.
Mostly older environments will have a database or some kind of storage that you have to deal with.
Here we are using a relational database as source of data.



## Docker Images
### DB - Postgresql

```bash
build.sh

```





## User and Roles
This Issuetracker will give you the choice to 
select roles for a user. The concept is quite simple.

A role will give you access to a dedicated view. 
There is no inheritance inside the roles. Every role must 
associated.

For example: To get access to the view to search for issues you will need to have the role **observer** 

The functionality inside the view should be based on permissions.
This is far away from perfect, but security roles and permissions
for an app is nothing easy. You need to think about this quite early during the
project phase. 

Key-Questions are:
* How to define the permissions an actions inside a dedicated view.
* What are the main blocks in your app
* What will be the main structure of the URL´s
* ...

 

### Roles 
The roles will give you access to different functionality.

* observer - This role will give you access to search for issues and see them, no editing.
* user - This role will give you access like observer plus CRUD for issues
* reports - This role will give you the right to the reports. 
* admin - all rights in the app, including user management.

### Defined users
You are able to login as different user. Every user is an example for 
a combination of roles.

* admin / admin: Admin User with all rights
* lead / lead: Lead Developer with the roles Observer, User and Reports
* developer / developer: Developer with the roles Observer and User
* customer / customer: An Observer with the roles Observer
* manager / manager: A regular Manager with the role Reports





Happy Coding.

if you have any questions: ping me on Twitter [https://twitter.com/SvenRuppert](https://twitter.com/SvenRuppert)
or via mail.
