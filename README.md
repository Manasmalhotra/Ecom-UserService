# E-commerce User Service

Welcome to the E-commerce User Service! </br>
This service provides functionalities for managing user details including registration, authentication, and address management.


## Tech Stack

**Programming Language:** Java

**Framework:** Spring Boot

**Databases:** MySQL

**Security:** JWT, OAuth 2


## Problem Statement

We require user details like their name, address, phone number, email, and other details to authenticate the users and ensure legitimate transactions on our website. 

Further, whenever we visit any e-commerce website I have seen that they verify the phone number and email address of th customer so that the customer can be contacted in case of any issue in finding their address and informing them about the progress of their order.

The user should also have the option to add as many addresses as they want to and select any of those while placing the order.

They should also be allowed to create a new address while placing the order. This would make the platform user-friendly and enhance the user experience. 

## Solution

For managing user authentication and providing users with the choice to choose their method of authentication, I implemented 2 authentication mechanisms in this service.

The first is a username and password-based registration, generating a JWT token and storing it in a custom sessions object.

The second one is receiving the required JWT through OAuth 2 authentication and validating it for user authentication on subsequent stages.

The custom session object also enabled me to limit the number of sessions for my users. For testing, I limited the number of user sessions to 1. So whenever a user tries to create a second session the JWT token in their previous session will be set to null.

For address management, I developed the complete set of model, controller, service, and repository modules for end-to-end management of customer addresses.


## Links to other services:</br>

Order Service: https://github.com/Manasmalhotra/Ecom-OrderService</br>
Product Service: https://github.com/Manasmalhotra/ProductService-new</br>
Payment Service: https://github.com/Manasmalhotra/Ecom-PaymentService</br>
Notification Service:https://github.com/Manasmalhotra/Ecom-NotificationService</br>
Service Registry: https://github.com/Manasmalhotra/Ecom-ServiceRegistry
