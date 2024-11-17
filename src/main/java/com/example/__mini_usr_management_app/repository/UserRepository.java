package com.example.__mini_usr_management_app.repository;

import com.example.__mini_usr_management_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserRepository extends JpaRepository<User , Serializable> {
    //  select * from user where email=?
    public User findByEmail(String email);

    // select * from user where email=? and user_pwd=?
    public User findByEmailAndUserPwd(String email , String pwd);
}
