package com.todo.reactiveDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskFacade {


  private final TaskRepository taskRepository;

  @Autowired
  public TaskFacade(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public Flux<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public Mono<Task> createTask(Task task) {
    return taskRepository.save(task);
  }

  public Mono<Task> updateTask(Long id, Task updatedTask) {
    return taskRepository.findById(id)
        .switchIfEmpty(Mono.error(new TaskNotFound("Task with id " + id + " not found")))
        .flatMap(existing -> {
          updatedTask.setId(id);
          return taskRepository.save(updatedTask);
        });
  }

  public Mono<Void> deleteTask(Long id) {
    return taskRepository.deleteById(id);
  }

//  private final Map<Long, Task> taskMap = new HashMap<>();
//  private long currentId = 1;
//
//  public Flux<Task> getAllTasks() {
//    return Flux.fromIterable(taskMap.values());
//  }
//
//  public Mono<Task> createTask(Task task) {
//    task.setId(currentId++);
//    taskMap.put(task.getId(), task);
//    return Mono.just(task);
//  }
//
//  public Mono<Task> updateTask(Long id, Task task) {
//    if (!taskMap.containsKey(id)) {
//      return Mono.error(new TaskNotFound("Task with id " + id + " not found"));
//    }
//    task.setId(id);
//    taskMap.put(id, task);
//    return Mono.just(task);
//  }
//
//  public Mono<Void> deleteTask(Long id) {
//    taskMap.remove(id);
//    return Mono.empty();
//  }
}

