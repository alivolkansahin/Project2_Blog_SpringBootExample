# APPLICATION EXCEPTION CODES

### Exception Type 50XX -> User Service Layer Exceptions
    5001 --> NO USERS EXIST EXCEPTION
    The error thrown when no user data exists in chosen database postgreSQL.
    It throws an "HTTP Code 404 Not Found" error to frontend.
    
    5002 --> NO USER FOUND BY ID EXCEPTION
    The error thrown when no user data is found for the given ID.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5003 --> USERNAME ALREADY EXISTS EXCEPTION
    The error thrown when a record with the same username is found.
    It throws an "HTTP Code 409 Conflict" error to frontend.

    5004 --> EMAIL ALREADY EXISTS EXCEPTION
    The error thrown when a record with the same email is found.
    It throws an "HTTP Code 409 Conflict" error to frontend.    
    
    5005 --> PASSWORDS NOT MATCH EXCEPTION
    The error thrown when the passwords provided in the "password" and "repassword"
    fields do not match during the create process.
    It throws an "HTTP Code 409 Conflict" error to frontend.

    5006 --> USER ALREADY DELETED EXCEPTION
    The error thrown when an attempt is made to delete an already deleted user.
    It throws an "HTTP Code 400 Bad Request" error to frontend.

### Exception Type 51XX -> Post Service Layer Exceptions
    5101 --> NO POSTS EXIST EXCEPTION
    The error thrown when no post data exists in chosen database postgreSQL.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5102 --> NO POST FOUND BY ID EXCEPTION
    The error thrown when no post data is found for the given ID.
    It throws an "HTTP Code 404 Not Found" error to frontend.    
    
    5103 --> NO POST FOUND BY USER EXCEPTION
    The error thrown when no post data is found for the given user.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5104 --> NO POST FOUND BY CATEGORY EXCEPTION
    The error thrown when no post data is found for the given category.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5105 --> NO POST FOUND BY SEARCH PARAMETER EXCEPTION
    The error thrown when no matching data is found in post title and post content
    for the given string search parameter.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5106 --> POST ALREADY DELETED EXCEPTION
    The error thrown when an attempt is made to delete an already deleted post.
    It throws an "HTTP Code 400 Bad Request" error to frontend.

### Exception Type 52XX -> Category Service Layer Exceptions
    5201 --> NO CATEGORY EXIST EXCEPTION
    The error thrown when no category data exists in chosen database postgreSQL.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5202 --> NO CATEGORY FOUND BY ID EXCEPTION
    The error thrown when no category data is found for the given ID.
    It throws an "HTTP Code 404 Not Found" error to frontend.    
    
    5203 --> NO CATEGORY FOUND BY NAME EXCEPTION
    The error thrown when no category data is found for the given name.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5204 --> CATEGORY NAME ALREADY EXISTS EXCEPTION
    The error thrown when a record with the same category name is found.
    It throws an "HTTP Code 409 Conflict" error to frontend.

    5205 --> CATEGORY ALREADY DELETED EXCEPTION
    The error thrown when an attempt is made to delete an already deleted category.
    It throws an "HTTP Code 400 Bad Request" error to frontend.
    
### Exception Type 53XX -> Comment Service Layer Exceptions
    5301 --> NO COMMENT EXIST EXCEPTION
    The error thrown when no comment data exists in chosen database postgreSQL.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5302 --> NO COMMENT FOUND BY ID EXCEPTION
    The error thrown when no comment data is found for the given ID.
    It throws an "HTTP Code 404 Not Found" error to frontend.    
    
    5303 --> NO COMMENT FOUND BY USER EXCEPTION
    The error thrown when no comment data is found for the given user.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5304 --> NO COMMENT FOUND ON POST EXCEPTION
    The error thrown when no comment data is found for the given post.
    It throws an "HTTP Code 404 Not Found" error to frontend.

    5305 --> COMMENT ALREADY DELETED EXCEPTION
    The error thrown when an attempt is made to delete an already deleted comment.
    It throws an "HTTP Code 400 Bad Request" error to frontend.

### Exception Type 54XX -> Controller Layer Exceptions
    5401 --> BLANK PARAMETER ENTRY EXCEPTION
    The error thrown when @PathVariable data intended for the method arrives blank 
    from the frontend.
    It throws an "HTTP Code 400 Bad Request" error to frontend.

    5402 --> BAD PARAMETERS FOR FOLLOW REQUEST EXCEPTION
    The error thrown when same user ID is used for seperate parameters for follow
    request method, indicating that user is trying to follow itself.
    It throws an "HTTP Code 400 Bad Request" error to frontend.

### Exception Type 55XX -> Dto Layer Exceptions
    5501 --> METHOD ARGUMENT NOT VALID EXCEPTION
    The error thrown when data transfer object annotations cannot be validated in
    controller layer with @Valid annotation 
    (ex. @NotBlank field received as blank from frontend)
    It throws an "HTTP Code 400 Bad Request" error to frontend.

### Exception Type 56XX -> Entity Layer Exceptions
    5601 --> CONSTRAINT VIOLATION EXCEPTION
    The error thrown when data is received from frontend passed dto annotations but 
    is not suitable for the annotations used in responsible entity class, therefore 
    hibernate transaction actions cannot be completed. 
    (ex. @PasswordDigitValidation, password field recevied without containing any digits.
    It throws an "HTTP Code 400 Bad Request" error to frontend.