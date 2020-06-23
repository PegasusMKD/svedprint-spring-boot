package com.svedprint.main.controllers;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.services.StudentDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    private StudentDtoService studentDtoService;

    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDto>> getAllStudents(@RequestHeader String token) {
        List<StudentDto> res = studentDtoService.getAllStudents(token);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto, @RequestHeader String token) {
        StudentDto res = studentDtoService.save(studentDto, token, false);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> update(@RequestBody StudentDto studentDto, @RequestHeader String token) {
        StudentDto res = studentDtoService.save(studentDto, token, true);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(res);
    }
}
