package com.svedprint.main.controllers;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.services.SubjectOrientationDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<SubjectOrientationDto> update(@RequestBody SubjectOrientationDto subjectOrientationDto, @RequestHeader String token) {
        SubjectOrientationDto res = subjectOrientationDtoService.save(subjectOrientationDto, token, true);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(res);
    }

}
