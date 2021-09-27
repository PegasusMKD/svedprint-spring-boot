package com.svedprint.main.controllers;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.services.SubjectOrientationDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subjectOrientations")
public class SubjectOrientationController {

	private final SubjectOrientationDtoService subjectOrientationDtoService;

	public SubjectOrientationController(SubjectOrientationDtoService subjectOrientationDtoService) {
		this.subjectOrientationDtoService = subjectOrientationDtoService;
	}

	@PostMapping
	public ResponseEntity<SubjectOrientationDto> create(@RequestBody SubjectOrientationDto subjectOrientationDto) {
		SubjectOrientationDto res = subjectOrientationDtoService.save(subjectOrientationDto, false);
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(res);
	}

	@GetMapping
	public ResponseEntity<List<SubjectOrientationDto>> get() {
		return ResponseEntity.ok(subjectOrientationDtoService.get());
	}

	@PutMapping
	public ResponseEntity<SubjectOrientationDto> update(@RequestBody SubjectOrientationDto subjectOrientationDto) {
		SubjectOrientationDto res = subjectOrientationDtoService.save(subjectOrientationDto, true);
		if (res == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(res);
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestBody SubjectOrientationDto subjectOrientationDto) {
		boolean res = subjectOrientationDtoService.delete(subjectOrientationDto);
		if (!res) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}
}
