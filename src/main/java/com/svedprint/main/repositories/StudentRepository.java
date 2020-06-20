package com.svedprint.main.repositories;

import com.svedprint.main.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

}
