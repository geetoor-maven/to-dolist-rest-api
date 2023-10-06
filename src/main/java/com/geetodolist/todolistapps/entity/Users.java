package com.geetodolist.todolistapps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mst_users")
public class Users {

    @Id
    @Column(name = "user_id", length = 50)
    private String user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "address")
    private String address;

    @Column(name = "create_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private Timestamp updateAt;
}
