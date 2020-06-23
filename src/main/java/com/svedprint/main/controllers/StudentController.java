package com.svedprint.main.controllers;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.services.StudentDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    private StudentDtoService studentDtoService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentDto>> getAllStudents(@RequestBody TeacherDto teacherDto) {
        List<StudentDto> res = studentDtoService.getAllStudents(teacherDto);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res);
    }
}
