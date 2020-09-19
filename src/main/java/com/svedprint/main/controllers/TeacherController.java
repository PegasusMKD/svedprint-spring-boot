package com.svedprint.main.controllers;

import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.services.TeacherDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

    @Autowired
    private TeacherDtoService teacherDtoService;

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherDto> login(@RequestBody TeacherDto teacherDto, @RequestHeader(name = "password") String password) {
        TeacherDto res = teacherDtoService.login(teacherDto, password);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(res);
    }

}
