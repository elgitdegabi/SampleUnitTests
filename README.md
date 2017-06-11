# SampleUnitTests #
## Disclaimer ##
This is not a commercial tool. It was development for test purpose only so doesn't have any warranty.

Feel free to test, use and/or modify.

For more details see the LICENSE file.

## Description ##
Here they are some ideas about testing using the different tools given by Spring Boot Test dependency. Maybe some tips are not consider as the best practices by the Spring Boot, Mockito, EasyMock and/or TDD folllowers but for me and my team they are so useful when we write and run the Unit Test for medium or high complexity classes.

If you found a better solution for one or more cases or you want to share your best practices with us, please, let me know and I will update this project.
 
## Features/Tips ##
* Includes H2 in memory database (check application.properties for the configuration values). You can access to consule by "/h2" end point
* Includes a sample of controller-> service -> DAO flow
* Includes Unit Tests using:
	* Mockito for Spring Boot 1.4 or higer
	* Mockito for Spring Boot 1.3.8 or lower
	* Spring Boot Test MVC feature
	* EasyMock 3.4
* Tested with Spring Boot versions:
	* 1.5.4.RELEASE
	* 1.4.1.RELEASE
	* 1.3.8.RELEASE

## End-points ##
### GET end-point: ###
http://localhost:8080/h2
http://localhost:8080/test/get

## GitHub repository: ##
### https://github.com/Gabotto/SampleUnitTests ###
## Contact ##
Let me know if you have any problem, comment or new ideas:
#### Wordpress: http://gabelopment.wordpress.com/ ####
#### Email: gabelopment@gmail.com ####

Edited on: 11th June 2017
