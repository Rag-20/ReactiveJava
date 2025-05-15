package com.todo.reactiveDemo;

public class TaskNotFound extends RuntimeException {

  public TaskNotFound(String message) {
    super(message);
  }
}
