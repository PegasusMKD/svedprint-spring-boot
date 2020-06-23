package com.svedprint.main.repositories;

import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findAllBySchoolClassOrderByNumberAsc(SchoolClass schoolClass);
}
