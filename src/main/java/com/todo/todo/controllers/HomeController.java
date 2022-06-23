package com.todo.todo.controllers;

import com.todo.todo.DB.DBManager;
import com.todo.todo.models.Task;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String index(Model model){
        ArrayList<Task> tasks = DBManager.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @PostMapping(value = "/addtask")
    public String addTasks(@RequestParam(name = "task_name") String task_name,
                           @RequestParam(name = "task_date")
                           @DateTimeFormat(pattern = "dd.mm.yyyy")
                           String task_date){
        Task task = new Task();
        task.setName(task_name);
        task.setDeadlineDate(task_date);
        task.setCompleted(false);
        DBManager.addTask(task);
        return "redirect:/";
    }

    @GetMapping(value = "/details/{taskId}")
    public String getDetails(@PathVariable(name = "taskId") Long id,
                            Model model){
            Task task = DBManager.getTask(id);
            model.addAttribute("task", task);
            return "details";
    }

    @PostMapping(value = "/edit")
    public String edit(@RequestParam(name = "task_id") Long id,
                       @RequestParam(name = "task_name") String name,
                       @RequestParam(name = "task_date") String date,
                       @RequestParam(name = "completed") String completed) {
        Task task = DBManager.getTask(id);
        task.setName(name);
        task.setDeadlineDate(date);
        boolean isCompleted = false;
        if (completed.equals("true"))
            isCompleted = true;
        task.setCompleted(isCompleted);
        DBManager.editTask(task);

        return "redirect:/";
    }

    //Decided to use get method because the delete button was inside another form
    @GetMapping(value = "/delete/{taskId}")
    public String deleteTask(@PathVariable(name = "taskId") Long id){
        Task task = DBManager.getTask(id);
        DBManager.deleteTask(task);
        return "redirect:/";
    }

}
