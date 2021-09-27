package com.svedprint.main.controllers;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.dtos.meta.PageResponse;
import com.svedprint.main.services.StudentDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/students")
public class StudentController {

	private final StudentDtoService studentDtoService;

	public StudentController(StudentDtoService studentDtoService) {
		this.studentDtoService = studentDtoService;
	}

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		List<StudentDto> res = studentDtoService.getAllStudents();
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(res);
	}

	@PostMapping(value = "/getOne", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDto> findOne(@RequestBody String id) {
		StudentDto res = studentDtoService.findOne(id);
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(res);
	}

	@PostMapping(value = "/page", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PageResponse<StudentDto>> findAll(@RequestBody StudentDto dto) {
		PageResponse<StudentDto> res = studentDtoService.findAll(dto);
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(res);
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
		StudentDto res = studentDtoService.save(studentDto);
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(res);
	}

	@PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDto> update(@RequestBody StudentDto studentDto) {
		StudentDto res = studentDtoService.save(studentDto);
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(res);
	}

	@DeleteMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDto> delete(@RequestBody StudentDto studentDto) {
		boolean res = studentDtoService.delete(studentDto);
		if (!res) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}
}
