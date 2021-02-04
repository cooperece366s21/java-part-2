package edu.cooper.ece366;

import static spark.Spark.get;

public class Spark {
  public static void main(String[] args) {
    //
    get("/", (request, response) -> "yo");

    spark.Spark.exception(
        Exception.class,
        (exception, request, response) -> {
          exception.printStackTrace();
        });
  }
}
