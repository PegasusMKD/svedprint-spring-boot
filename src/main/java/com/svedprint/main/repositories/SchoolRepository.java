package com.svedprint.main.repositories;

import com.svedprint.main.models.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, String> {

}
