package com.todo.reactiveDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService service;

  @Autowired
  public TaskController(TaskService service) {
    this.service = service;
  }

  @GetMapping
  public Flux<Task> getAll() {
    return service.getAllTasks();
  }

  @PostMapping
  public Mono<Task> create(@RequestBody Task task) {
    return service.createTask(task);
  }

  @PutMapping("/{id}")
  public Mono<Task> update(@PathVariable Long id, @RequestBody Task task) {
    return service.updateTask(id, task);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable Long id) {
    return service.deleteTask(id);
  }
}
