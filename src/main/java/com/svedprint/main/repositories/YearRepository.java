package com.svedprint.main.repositories;

import com.svedprint.main.models.Student;
import com.svedprint.main.models.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface YearRepository extends JpaRepository<Year, String>, QuerydslPredicateExecutor<Year> {

}
