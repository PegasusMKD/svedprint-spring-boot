package com.svedprint.main.repositories;

import com.svedprint.main.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Teacher findByUsername(String username);
}
