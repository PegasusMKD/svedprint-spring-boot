package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.SchoolClassMapper;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.repositories.SchoolClassRepository;
import com.svedprint.main.repositories.SubjectOrientationRepository;
import com.svedprint.main.repositories.YearRepository;
import com.svedprint.main.services.decorators.SchoolClassDtoDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

import static java.util.Optional.ofNullable;

@Service
public class SchoolClassDtoService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private SchoolClassMapper schoolClassMapper;

    @Autowired
    private SubjectOrientationRepository subjectOrientationRepository;

    @Autowired
    private TeacherDtoService teacherDtoService;


    public SchoolClassDto findOne(String id) {
        return schoolClassMapper.toDto(schoolClassRepository.getOne(id));
    }

    public SchoolClassDto update(SchoolClassDto schoolClassDto, boolean update) {

        // TODO: Differentiate the operations between Admin and Teacher

        if (schoolClassDto == null) {
            return null;
        }

        final SchoolClass schoolClass = schoolClassDto.isIdSet() ? schoolClassRepository.getOne(schoolClassDto.getId()) : new SchoolClass();

        String teacherId = ofNullable(schoolClassDto.getTeacher()).map(TeacherDto::getId)
                .orElse(ofNullable(schoolClassDto.getTeacher()).map(TeacherDto::getToken)
                        .orElse(null));

        if (teacherId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_USER_PROVIDED);
        } else {
            schoolClassDto.setTeacher(teacherDtoService.findOne(teacherId, teacherId));
        }

        if (schoolClass.getId() == null) {
            schoolClass.setStudents(new ArrayList<>());
            schoolClass.setSubjectOrientations(new HashSet<>());
        } else {
            // If any updates should happen on the students, it should be from the students service, not from the SchoolClass services
            schoolClassDto.setStudents(null);
        }

        SchoolClassDtoDecorator decorator = SchoolClassDtoDecorator.builder().build();
        schoolClassMapper.decorate(schoolClassDto, decorator);

        // TODO: Check if this iteration can be removed (might be properly covered by JPA and mappers)
        schoolClassDto.getSubjectOrientations().forEach(subjectOrientationDto -> schoolClass.getSubjectOrientations()
                .add(subjectOrientationRepository.getOne(subjectOrientationDto.getId())));
        schoolClassDto.setSubjectOrientations(null);

        schoolClassMapper.updateEntity(decorator.init(schoolClass, update, yearRepository), schoolClass);
        return schoolClassMapper.toDto(schoolClassRepository.save(schoolClass));
    }

}
