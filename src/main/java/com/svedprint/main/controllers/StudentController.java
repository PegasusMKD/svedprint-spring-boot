package com.svedprint.main.controllers;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.dtos.meta.PageResponse;
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

    @PostMapping(name = "/getOne", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
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

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> delete(@RequestBody StudentDto studentDto, @RequestHeader String token) {
        boolean res = studentDtoService.delete(studentDto, token);
        if (!res) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
