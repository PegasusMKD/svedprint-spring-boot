package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.StudentMapper;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.Student;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.repositories.SchoolClassRepository;
import com.svedprint.main.repositories.StudentRepository;
import com.svedprint.main.repositories.SubjectOrientationRepository;
import com.svedprint.main.services.decorators.StudentDtoDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class StudentDtoService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SubjectOrientationRepository subjectOrientationRepository;

    @Autowired
    private TeacherDtoService teacherDtoService;

    public StudentDto save(StudentDto dto, boolean update) {
        if (dto == null) {
            return null;
        }

        final Student student = dto.isIdSet() ? studentRepository.getOne(dto.getId()) : new Student();

        String classId = ofNullable(dto.getSchoolClass()).map(SchoolClassDto::getId).orElse(ofNullable(student.getSchoolClass()).map(SchoolClass::getId).orElse(null)); // null should be changed to teacher.getSchoolClass()
        if (classId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_CLASS_ASSIGNED);
        } else {
            student.setSchoolClass(schoolClassRepository.getOne(classId));
        }

        String subjectOrientationId = ofNullable(dto.getSubjectOrientation()).map(SubjectOrientationDto::getId)
                .orElse(ofNullable(student.getSubjectOrientation()).map(SubjectOrientation::getId).orElse(null));
        if (subjectOrientationId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
        } else {
            student.setSubjectOrientation(subjectOrientationRepository.getOne(subjectOrientationId));
        }

        StudentDtoDecorator decorator = StudentDtoDecorator.builder().build();
        studentMapper.decorate(dto, decorator);
        studentMapper.updateEntity(decorator.init(student, update), student);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public Set<StudentDto> getAllStudents(TeacherDto teacherDto) {
        return teacherDtoService.findEntityByToken(teacherDto.getToken()).getSchoolClass().getStudents()
                .stream().map(student -> studentMapper.toDto(student)).collect(Collectors.toSet());
    }
}
