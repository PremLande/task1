package com.example.exceltospring.repository;

import com.example.exceltospring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {

}
