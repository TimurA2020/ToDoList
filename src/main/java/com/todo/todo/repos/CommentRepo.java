package com.todo.todo.repos;

import com.todo.todo.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.task.id = :task_id")
    public List<Comment> findByTask(@Param("task_id") Long id);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.task.id = :task_id")
    public void deleteByTask(@Param("task_id") Long id);
}
