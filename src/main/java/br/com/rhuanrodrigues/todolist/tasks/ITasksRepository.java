package br.com.rhuanrodrigues.todolist.tasks;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITasksRepository extends JpaRepository<TaskModel, UUID> {
    
}
