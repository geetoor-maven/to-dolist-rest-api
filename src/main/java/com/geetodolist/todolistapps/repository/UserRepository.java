package com.geetodolist.todolistapps.repository;

import com.geetodolist.todolistapps.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String > {

}
