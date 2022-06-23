package com.todo.todo.DB;

import com.todo.todo.models.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DBManager {
    static ArrayList<Task> allTasks = new ArrayList<>();
    private static long id = 1L;

    public static void addTask(Task task){
        task.setId(id);
        allTasks.add(task);
        id++;
    }
    public static ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    public static Task getTask(Long id){
        return getAllTasks().stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
    }

    public static void editTask(Task task){
        getAllTasks().stream().filter(a -> a.getId() == task.getId()).forEach(a -> a.setName(task.getName()));
        getAllTasks().stream().filter(a -> a.getId() == task.getId()).forEach(a -> a.setDeadlineDate(task.getDeadlineDate()));
        getAllTasks().stream().filter(a -> a.getId() == task.getId()).forEach(a -> a.setCompleted(task.isCompleted()));
    }

    public static void deleteTask(Task task){
//        getAllTasks().stream().filter(a -> a.getId() == id).forEach(allTasks.remove(a));
        allTasks.remove(task);
    }
}
