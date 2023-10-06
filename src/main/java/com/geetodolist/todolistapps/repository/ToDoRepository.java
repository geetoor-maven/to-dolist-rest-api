package com.geetodolist.todolistapps.repository;

import com.geetodolist.todolistapps.entity.ToDos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDos, String> {
}
