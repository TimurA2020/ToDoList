package com.todo.todo.repos;

import com.todo.todo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TasksRepo extends JpaRepository<Task, Long> {

//    @Query("SELECT task FROM Task task WHERE task.folder = ")
//    List<Task> limit();
    public List<Task> findByFolder_Id(Long id);
}
