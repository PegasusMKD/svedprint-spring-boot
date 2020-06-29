package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.StudentMapper;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.Student;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.models.Teacher;
import com.svedprint.main.repositories.SchoolClassRepository;
import com.svedprint.main.repositories.StudentRepository;
import com.svedprint.main.repositories.SubjectOrientationRepository;
import com.svedprint.main.services.decorators.StudentDtoDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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


    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents(String token) {
        return teacherDtoService.findEntityByToken(token).getSchoolClass().getStudents()
                .stream().map(student -> studentMapper.toDto(student)).collect(Collectors.toList());
    }

    @Transactional
    public StudentDto save(StudentDto dto, String token, boolean update) {
        if (dto == null) {
            return null;
        }

        final Student student = dto.isIdSet() ? studentRepository.getOne(dto.getId()) : new Student();
        final Teacher teacher = teacherDtoService.findEntityByToken(token);

        String classId = ofNullable(dto.getSchoolClass()).map(SchoolClassDto::getId).orElse(ofNullable(student.getSchoolClass()).map(SchoolClass::getId).orElse(teacher.getSchoolClass().getId())); // null should be changed to teacher.getSchoolClass()
        if (classId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_CLASS_ASSIGNED);
        } else {
            student.setSchoolClass(schoolClassRepository.getOne(classId));
        }

        getDecoratorNumber(teacher.getSchoolClass().getStudents(), student, dto); // TODO: Might still be bugged when transferring student from class to class if "number" is the same
        String subjectOrientationId = ofNullable(dto.getSubjectOrientation()).map(SubjectOrientationDto::getId)
                .orElse(ofNullable(student.getSubjectOrientation()).map(SubjectOrientation::getId).orElse(null));
        if (subjectOrientationId == null) { // TODO: Rework it so that it searches in the orientations of the class
            throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
        } else {
            SubjectOrientation subjectOrientation = subjectOrientationRepository.getOne(subjectOrientationId);
            if (teacher.getSchoolClass().getSubjectOrientations().contains(subjectOrientation)) {
                // TODO: Add grades handler for changing subjectOrientation
                handleSubjectOrientationGrades(student, subjectOrientation);
                //
                student.setSubjectOrientation(subjectOrientation);
            } else {
                throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCIONALITY);
            }
        }

        StudentDtoDecorator decorator = StudentDtoDecorator.builder().build();
        studentMapper.decorate(dto, decorator);
        studentMapper.updateEntity(decorator.init(student, update), student);
        return studentMapper.toDto(studentRepository.save(student));
    }


    private void handleSubjectOrientationGrades(Student entity, SubjectOrientation newSubjectOrientation) {
        List<Integer> entityGrades = entity.getGrades();
        List<String> oldOrientationSubjects = new ArrayList<>(entity.getSubjectOrientation().getSubjects());
        List<String> newOrientationSubjects = new ArrayList<>(newSubjectOrientation.getSubjects());
        for (String subject : oldOrientationSubjects) {
            if (!newOrientationSubjects.contains(subject)) {
                int idxSubject = oldOrientationSubjects.indexOf(subject);
                entityGrades.remove(idxSubject);
                oldOrientationSubjects.remove(idxSubject);
            }
        }

        for (String subject : newOrientationSubjects) {
            if (!oldOrientationSubjects.contains(subject)) {
                int idxSubject = newOrientationSubjects.indexOf(subject);
                entityGrades.add(0);
            }
        }

        entity.setGrades(entityGrades);
    }

    @Transactional
    public StudentDto oldSave(StudentDto dto, boolean update) {
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

    @Transactional
    public boolean delete(StudentDto studentDto, String token) {
        if (studentDto == null || studentDto.getId() == null) {
            return false;
        }
        final Teacher teacher = teacherDtoService.findEntityByToken(token);
        if (teacher.getSchoolClass().getStudents().stream().map(Student::getId).collect(Collectors.toList()).contains(studentDto.getId())) {
            studentRepository.deleteById(studentDto.getId());
            return true;
        } else {
            throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCIONALITY);
        }
    }

    @Transactional
    public void getDecoratorNumber(List<Student> students, Student entity, StudentDto studentDto) {
        if(studentDto.getNumber() != null && entity.getNumber() == null) {
            return;
        }

        if (studentDto.getNumber() == null && entity.getNumber() == null) {
            studentDto.setNumber(students.size() + 1);

        } else if (studentDto.getNumber() == null && entity.getNumber() != null) {
            studentDto.setNumber(entity.getNumber());

        } else if (studentDto.getNumber() > entity.getNumber()) {
            List<Student> iterableStudents = new ArrayList<>(students.subList(entity.getNumber(), studentDto.getNumber()));
            for (Student student : iterableStudents) {
                student.setNumber(student.getNumber() - 1);
            }
            studentRepository.saveAll(iterableStudents);

        } else if (studentDto.getNumber() < entity.getNumber()) {
            List<Student> iterableStudents = new ArrayList<>(students.subList(studentDto.getNumber() - 1, entity.getNumber() - 1));
            System.out.println(iterableStudents.size());
            for (Student student : iterableStudents) {
                student.setNumber(student.getNumber() + 1);
            }
            studentRepository.saveAll(iterableStudents);
        }
    }
}
