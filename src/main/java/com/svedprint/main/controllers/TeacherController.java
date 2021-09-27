package com.svedprint.main.controllers;

import com.svedprint.main.configs.oauth.security.UserContext;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.services.TeacherDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

	private final TeacherDtoService teacherDtoService;

	public TeacherController(TeacherDtoService teacherDtoService) {
		this.teacherDtoService = teacherDtoService;
	}

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<TeacherDto> getTeacherByUsername() {
		return ResponseEntity.ok(teacherDtoService.findByUsername(UserContext.getUsername()));
	}
}
