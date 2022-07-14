package com.todo.todo.controllers;

import com.todo.todo.models.Comment;
import com.todo.todo.models.Folder;
import com.todo.todo.models.Task;
import com.todo.todo.models.TaskCategory;
import com.todo.todo.repos.CategoryRepo;
import com.todo.todo.repos.CommentRepo;
import com.todo.todo.repos.FolderRepo;
import com.todo.todo.repos.TasksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private TasksRepo taskRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CommentRepo commentRepo;

    @GetMapping(value = "/")
    public String index(Model model){
        List<Folder> folders = folderRepo.findAll();
        model.addAttribute("folders", folders);
        return "index";
    }

    @GetMapping(value = "/folderdetails/{folderId}")
    public String getDetails(@PathVariable(name = "folderId") Long id,
    Model model){
        Folder folder = folderRepo.getReferenceById(id);
        List<Task>tasks = taskRepo.findByFolder_Id(id);
        model.addAttribute("tasks", tasks);
        model.addAttribute("folder", folder);
        return "folderdetails";
    }

    @PostMapping(value = "/addfolder")
    public String addFolder(@RequestParam(name = "folder_name") String name){
        Folder folder = new Folder();
        folder.setName(name);
        folderRepo.save(folder);
        return "redirect:/";
    }

    @PostMapping(value = "/addtask")
    public String addTask(@RequestParam(name = "task_title") String title,
                          @RequestParam(name = "task_description") String description,
                          @RequestParam(name = "folder_id") Long folder_id){
        Folder folder = folderRepo.getReferenceById(folder_id);
        if(folder != null){
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(0);
            task.setFolder(folder);
            taskRepo.save(task);
            return "redirect:/folderdetails/" + folder_id;
        }
        return "taskCreationError";
    }

    @PostMapping(value = "/deletefolder")
    public String deletefolder(@RequestParam(name = "folder_id") Long id){
        folderRepo.deleteById(id);
        return "redirect:/";
    }

    @PostMapping(value = "/addcategory")
    public String addCategory(@RequestParam(name = "category_name") String name,
                              @RequestParam(name = "folder_id") Long folder_id){
        Folder folder = folderRepo.getReferenceById(folder_id);
        List<TaskCategory> categories = folder.getTaskCategories();
        if(categories == null)
            categories = new ArrayList<>();
        TaskCategory category = new TaskCategory();
        category.setName(name);
        categories.add(category);
        categoryRepo.save(category);
        folder.setTaskCategories(categories);
        folderRepo.save(folder);
        return "redirect:/folderdetails/" + folder_id;
    }

    @PostMapping(value = "/deletecategory")
    public String deleteCategory(@RequestParam(name = "category_id") Long category_id,
                                 @RequestParam(name = "folder_id") Long folder_id){
        Folder folder = folderRepo.getReferenceById(folder_id);
        TaskCategory category = categoryRepo.getReferenceById(category_id);
        folder.getTaskCategories().remove(category);
        folderRepo.save(folder);
        categoryRepo.deleteById(category_id);
        return "redirect:/folderdetails/" + folder_id;
    }

    @PostMapping(value = "/deletetask")
    public String deleteTask(@RequestParam(name = "task_id") Long task_id){
        Task task = taskRepo.getReferenceById(task_id);
        taskRepo.deleteById(task_id);
        return "redirect:/folderdetails/" + task.getFolder().getId();
    }

    @GetMapping("/details/{taskId}")
    public String getTaskDetail(@PathVariable(name = "taskId") Long id,
                                Model model){
        Task task = taskRepo.getReferenceById(id);
        List<Comment> comments = commentRepo.findByTask(id);
        model.addAttribute("comments", comments);
        model.addAttribute("task", task);
        return "details";
    }

    @PostMapping("/edittask")
    public String editTask(@RequestParam(name = "task_id") Long id,
                           @RequestParam(name = "task_title") String title,
                           @RequestParam(name = "task_description") String description,
                           @RequestParam(name = "status") int status){
        Task task = taskRepo.getReferenceById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        Long folder_id = task.getFolder().getId();
        taskRepo.save(task);
        return "redirect:/folderdetails/" + folder_id;
    }

    @PostMapping("/addcomment")
    public String addComment(@RequestParam(name = "comment") String name,
                             @RequestParam(name = "task_id") Long task_id){
        Comment comment = new Comment();
        comment.setComment(name);
        Task task = taskRepo.getReferenceById(task_id);
        comment.setTask(task);
        commentRepo.save(comment);
        return "redirect:/details/"+task_id;
    }

    @GetMapping(value = "/taskCreationError")
    public String taskCreationError(){
        return "taskCreationError";
    }
}
