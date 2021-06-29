package com.svedprint.main.repositories;

import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, String>, QuerydslPredicateExecutor<SchoolClass> {

}
