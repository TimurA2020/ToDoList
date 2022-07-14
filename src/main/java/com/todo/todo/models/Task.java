package com.todo.todo.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int status;

    @ManyToOne
    private Folder folder;

    public static String getStatus(int status){
        String string = "TO DO";
        if(status == 1)
            string = "In Progress";
        if(status == 2)
            string = "Done";
        if(status == 3)
            string = "Failed";

        return string;
    }

    public static String getColor(int status){
        String color = "blue";
        if(status == 1)
            color = "orange";
        if(status == 2)
            color = "green";
        if(status == 3)
            color = "red";

        return color;
    }
}
