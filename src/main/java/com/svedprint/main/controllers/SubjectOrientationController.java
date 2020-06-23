package com.svedprint.main.controllers;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.services.SubjectOrientationDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subjectOrientations")
public class SubjectOrientationController {

    @Autowired
    private SubjectOrientationDtoService subjectOrientationDtoService;

    @PostMapping
    public ResponseEntity<SubjectOrientationDto> create(@RequestBody SubjectOrientationDto subjectOrientationDto, @RequestHeader String token) {
        SubjectOrientationDto res = subjectOrientationDtoService.save(subjectOrientationDto, token, false);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<SubjectOrientationDto>> get(@RequestHeader String token) {
        return ResponseEntity.ok(subjectOrientationDtoService.get(token));
    }

    @PutMapping
    public ResponseEntity<SubjectOrientationDto> update(@RequestBody SubjectOrientationDto subjectOrientationDto, @RequestHeader String token) {
        SubjectOrientationDto res = subjectOrientationDtoService.save(subjectOrientationDto, token, true);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(res);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody SubjectOrientationDto subjectOrientationDto, @RequestHeader String token) {
        boolean res = subjectOrientationDtoService.delete(subjectOrientationDto, token);

        if (!res) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
