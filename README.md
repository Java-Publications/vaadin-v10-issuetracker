<center>
<a href="https://vaadin.com">
 <img src="https://vaadin.com/images/hero-reindeer.svg" width="200" height="200" /></a>
</center>


#Vaadin V10 - Demo application - Issuetracker

In this example we are using the database first approach.
Mostly older environments will have a database or some kind of storage that you have to deal with.
Here we are using a relational database as source of data.

## Status
[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://github.com/Java-Publications/vaadin-v10-issuetracker/blob/master/LICENSE.txt)

demo Badges
[![Latest Stable Version](https://img.shields.io/badge/Latest%20Stable%20Version-1.2.0.Final-blue.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.mapstruct%20AND%20v%3A1.*.Final)
[![Latest Version](https://img.shields.io/maven-central/v/org.mapstruct/mapstruct-processor.svg?maxAge=3600&label=Latest%20Release)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.mapstruct)
[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://github.com/mapstruct/mapstruct/blob/master/LICENSE.txt)

[![Build Status](https://img.shields.io/travis/mapstruct/mapstruct.svg)](https://travis-ci.org/mapstruct/mapstruct)
[![Coverage Status](https://img.shields.io/codecov/c/github/mapstruct/mapstruct.svg)](https://codecov.io/gh/mapstruct/mapstruct)
[![Gitter](https://img.shields.io/gitter/room/mapstruct/mapstruct.svg)](https://gitter.im/mapstruct/mapstruct-users)

## Description
### The UseCases

### the Persistence Model / ERD

### Modelmapping / JPA / Data Verification / jUnit
* Check if for every DB Entry ( UserRole) the is a corresponding enum





## Modules
Maven Modules for
* persistence mapping from database to JPA Entities
* JPA Entities to Business Pojos
* UI infrastructure 


## Docker Images
### DB - Postgresql

```bash
build.sh

```






## User and Roles
### Challenge
The connection between Annotations (static) and Values inside the database.
For example the UserRole.
Let´s assume the UserRole is modelled as ENUM. This makes it easy to use inside Code.
The developer can only use existing values, refactoring will change Names or Values
immediately. But how to make this coherent to the values inside the database.
Mapping the Enum to a String as embedded Value of an Entity is only the half truth.

Why?
The database could introduce additional values and this would not reflect into the webapp.
How to deal with changes on the Sourcecode side? The embedded values inside the database are out of sync.

What could be done?
Every module will provide a ValidationService. This Service should be used during application startup
to make sure everything is in a valid and coherent state.


## User is not an Assignee 
Here in this system the User that is able to login is at the same time the Assignee.
If the Module Administration and BugTracker would be independent,
the would be a mapping between the class User and Assignee/Reporter/Watcher 

If it is needed to be a valid System user to be an Assignee, 
the Module should be able to map.
How to deal with System Users that are assigned to an issue but not able to login anymore?



Happy Coding.

if you have any questions: ping me on Twitter [https://twitter.com/SvenRuppert](https://twitter.com/SvenRuppert)
or via mail.
