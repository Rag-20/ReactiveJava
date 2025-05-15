package com.todo.reactiveDemo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {

  private final TaskFacade facade;

  public TaskService(TaskFacade facade) {
    this.facade = facade;
  }

  public Flux<Task> getAllTasks() {
    return facade.getAllTasks();
  }

  public Mono<Task> createTask(Task task) {
    return facade.createTask(task);
  }

  public Mono<Task> updateTask(Long id, Task task) {
    return facade.updateTask(id, task);
  }

  public Mono<Void> deleteTask(Long id) {
    return facade.deleteTask(id);
  }
}
