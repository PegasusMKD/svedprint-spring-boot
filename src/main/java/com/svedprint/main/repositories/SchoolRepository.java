package com.svedprint.main.repositories;

import com.svedprint.main.models.School;
import com.svedprint.main.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SchoolRepository extends JpaRepository<School, String>, QuerydslPredicateExecutor<School> {

}
