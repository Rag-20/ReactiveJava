package com.todo.reactiveDemo;

import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TaskFacadeTest {

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskFacade taskFacade;

  @Test
  void getAllTasks_ShouldReturnAllTasks() {
    //given
    Task task1 = new Task(1L, "Task 1", true);
    Task task2 = new Task(2L, "Task 2", false);
    List<Task> tasks = Arrays.asList(task1, task2);

    when(taskRepository.findAll()).thenReturn(Flux.fromIterable(tasks));

    // Act & Assert
    StepVerifier.create(taskFacade.getAllTasks())
        .expectNext(task1)
        .expectNext(task2)
        .verifyComplete();
  }

  @Test
  void createTask_ShouldSaveAndReturnTask() {
    // given
    Task task = new Task(1L, "New Task", true);

    when(taskRepository.save(task)).thenReturn(Mono.just(task));

    // Act & Assert
    StepVerifier.create(taskFacade.createTask(task))
        .expectNext(task)
        .verifyComplete();
  }

  @Test
  void updateTask_ShouldUpdateAndReturnTask() {
    // given
    long id = 1L;
    Task existingTask = new Task(id, "Existing Task", true);
    Task updatedTask = new Task(id, "Updated Task", false);
    Task savedTask = new Task(id, "Updated Task", false);

    when(taskRepository.findById(id)).thenReturn(Mono.just(existingTask));
    when(taskRepository.save(updatedTask)).thenReturn(Mono.just(savedTask));

    // Act & Assert
    StepVerifier.create(taskFacade.updateTask(id, updatedTask))
        .expectNext(savedTask)
        .verifyComplete();
  }

  @Test
  void updateTask_ShouldThrowErrorWhenTaskNotFound() {
    // given
    long id = 1L;
    Task updatedTask = new Task(id, "Updated Task", false);

    when(taskRepository.findById(id)).thenReturn(Mono.empty());

    // Act & Assert
    StepVerifier.create(taskFacade.updateTask(id, updatedTask))
        .expectErrorMatches(throwable -> throwable instanceof TaskNotFound &&
            throwable.getMessage().equals("Task with id " + id + " not found"))
        .verify();
  }
}