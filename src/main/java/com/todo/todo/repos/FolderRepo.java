package com.todo.todo.repos;

import com.todo.todo.models.Folder;

import com.todo.todo.models.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FolderRepo extends JpaRepository<Folder, Long> {

}
