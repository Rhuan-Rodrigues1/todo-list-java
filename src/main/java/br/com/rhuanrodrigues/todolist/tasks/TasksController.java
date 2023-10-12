package br.com.rhuanrodrigues.todolist.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private ITasksRepository iTasksRepository;

    public ResponseEntity create(@RequestBody TaskModel taskModel) {
        try {

            TaskModel task = this.iTasksRepository.save(taskModel);
    
            return ResponseEntity.status(200).body(task);
            
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage() + "\n" + e.getStackTrace());
        }

    }
}
