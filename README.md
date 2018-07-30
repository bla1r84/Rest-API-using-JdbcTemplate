In this solution, which remains more loyal to the original code given, JdbcTemplate was used.
UserRepository
--------------
Several methods were created in the UserRepository in order to complete the various CRUD functions.
insertUser(): method to insert a new user to the database.
updateUser(): method to update a user in the database.
findById(): takes an ID and returns an object of type User.
It is interesting to note that this method will throw an exception if no user is found. That particular trait was used later on in the controller.
findByName(): takes a name and returns an object of type User.
findUser(): takes a full User object and search for a user in the database with that object's properties (except the ID).
findAllUsers(): returns a list of all the users in the database.
deleteUser(): deletes a user with the provided ID. Will go through (no exception thrown) even if no user has that ID.
deleteAllUsers(): deletes all users from the database.

UserServiceImpl
---------------
The UserServiceImpl class implements all the methods from the UserService interface by using the methods of the UserRepository.
What should be noted here is that in an effort to modify the User model as little as possible (with @Id and @GeneratedValue annotations), the saveUser() method also calculates the ID of the user to be inserted. It first finds all the users using the findAllUsers method of the service, and assigns an ID to the new user.
The method isUserExist() takes a User object and, using the findUser() method of the UserRepository, returns true or false if such a user exists or not respectively.

RestApiController
-----------------
The main controller (RestApiController) uses the methods of the UserServiceImpl class to respond to the client.
listAllUsers(): finds all the users, adds a note to the log file and then returns an ok response status to the client, as well as a JSON of the complete list of users.
getUser(): Same as above, except that it finds an returns only one user. If no user is found, it throws an exception. This is the dealt with in the RestExceptionHandler controller (annotated as @ControllerAdvice), and in particular in the handleEmptyResultDataAccessException() method of that controller, which returns a 404 status code and a message that no user was found.
createUser(): An @Valid annotation was added here in order to check whether the name property is either missing or is empty. To this end, an @NotBlank annotation was added to the name property in the User model. Otherwise this would have to be handled manually in the controller (by checking if the given name is valid).
The location of the new user is also generated here and returned with the ResponseEntity (along with with 201 status code and new User details)
updateUser(): Updates the user by first setting its ID to the given path variable and then calling the respective method of the UserService.
deleteUser(): first tries to find the user. If it is not found, an exception is thrown, thus it doesn't continue needlessly and also a correct response is returned.
deleteAllUsers(): deletes all the users.

CustomErrorType
---------------
CustomErrorType was edited to include the HttpStatus and a list of all the errors.

Swagger
-------
A Swagger class was inserted and can be accessed through http://localhost:8080/SpringBootRestApi/swagger-ui.html. It provides automatic documentation for the Rest Service. Note: The @Api annotation was added to the main controller. That, in combination with the ".apis(RequestHandlerSelectors.withClassAnnotation(Api.class))" line in the Swagger's configuration makes sure that only the main controller will appear in Swagger (RestExceptionHandler is ommitted).
