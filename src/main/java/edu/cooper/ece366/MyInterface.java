package edu.cooper.ece366;

public interface MyInterface {

  String get(Integer i);

  default String getTwice(Integer i) {
    return get(i)+get(i);
  }

}
