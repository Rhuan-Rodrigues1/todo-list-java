package br.com.rhuanrodrigues.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rhuanrodrigues.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private ITasksRepository iTasksRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        try {
            Object idUser = request.getAttribute("idUser");
            taskModel.setIdUser((UUID) idUser);
            
            LocalDateTime current = LocalDateTime.now();

            if (current.isAfter(taskModel.getStartAt()) || current.isAfter(taskModel.getEndAt())) {
                 return ResponseEntity.status(400).body("Datas inválidas");     
            }

            if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
                return ResponseEntity.status(400).body("Datas inválidas");
            }

            TaskModel task = this.iTasksRepository.save(taskModel);
    
            return ResponseEntity.status(200).body(task);
            
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage() + "\n" + e.getStackTrace());
        }

    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        Object idUser = request.getAttribute("idUser");
        List<TaskModel> listTasks = this.iTasksRepository.findByIdUser((UUID) idUser);

        return listTasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

        Object idUser = request.getAttribute("idUser");
        TaskModel task = this.iTasksRepository.findById(id).orElse(null);

        if(task == null) {
            return ResponseEntity.status(400).body("Tarefa não encontrada");
        }

        if(!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(400).body("Ação invalida");
        }


        Utils.copyNonNullProperties(taskModel, task);

        
        var save = this.iTasksRepository.save(task);

        return ResponseEntity.ok().body(this.iTasksRepository.save(save));
    }
}
