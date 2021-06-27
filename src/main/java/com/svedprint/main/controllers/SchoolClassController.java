package com.svedprint.main.controllers;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.services.SchoolClassDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/schoolClass")
public class SchoolClassController {

	private final SchoolClassDtoService schoolClassDtoService;

	public SchoolClassController(SchoolClassDtoService schoolClassDtoService) {
		this.schoolClassDtoService = schoolClassDtoService;
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<SchoolClassDto> getSchoolClassByUser(@RequestBody TeacherDto teacherDto) {
		SchoolClassDto res = schoolClassDtoService.getSchoolClassByUser(teacherDto);
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(res);
	}
}
