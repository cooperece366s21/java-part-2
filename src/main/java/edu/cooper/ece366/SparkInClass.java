package edu.cooper.ece366;

import static spark.Spark.*;

public class SparkInClass {
  public static void main(String[] args) {
    // register route
    get("/", (request, response) -> "hello");
    // parameterize route in various ways (query param, path arg)
    get("/hey/:user", ((request, response) -> request.params(":user")));

    class User {

    }

    // how do i translate a User object into a json object?
    // how do i translate a json object into a User object?
    // what if shit goes wrong? --> input / output validation?

    // how do i connect to a database? how do i store and retrieve and update info?

    // exception handling

    // server implementation

    // translate from request to app logic
    // translate from app data to response object
    // translate from app logic to database and vise versa

    // "deserialization" and "serialization"
    // "unmarshalling" and "marshalling"
    // --> jackson library in java
  }
}
