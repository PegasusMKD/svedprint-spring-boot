package com.svedprint.main.repositories;

import com.svedprint.main.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
	Teacher findByUsername(String username);

	Optional<Teacher> findByIdOrToken(String id, String token);

	Optional<Teacher> findByToken(String token);
}
