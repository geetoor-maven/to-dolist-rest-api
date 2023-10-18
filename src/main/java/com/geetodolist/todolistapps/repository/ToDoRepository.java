package com.geetodolist.todolistapps.repository;

import com.geetodolist.todolistapps.entity.ToDos;
import com.geetodolist.todolistapps.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDos, String> {
    Page<ToDos> findByUsers(Users users, Pageable page);
    Optional<ToDos> findByUsersAndTodoId(Users users, String todoId);
}
