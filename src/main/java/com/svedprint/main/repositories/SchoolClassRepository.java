package com.svedprint.main.repositories;

import com.svedprint.main.models.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, String> {

}
