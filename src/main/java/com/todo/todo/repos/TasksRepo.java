package com.todo.todo.repos;

import com.todo.todo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TasksRepo extends JpaRepository<Task, Long> {

//    @Query("SELECT task FROM Task task WHERE task.folder = ")
//    List<Task> limit();
    public List<Task> findByFolder_Id(Long id);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.folder.id = :folder_id")
    public void deleteByFolder(@Param("folder_id") Long id);
}
