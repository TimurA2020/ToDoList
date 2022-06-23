package com.todo.todo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    Long id;
    String name;
    String deadlineDate;
    boolean isCompleted;


}
