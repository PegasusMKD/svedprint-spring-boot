package com.svedprint.main.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.dtos.meta.PageResponse;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.StudentMapper;
import com.svedprint.main.models.*;
import com.svedprint.main.models.QStudent;
import com.svedprint.main.repositories.SchoolClassRepository;
import com.svedprint.main.repositories.StudentRepository;
import com.svedprint.main.repositories.SubjectOrientationRepository;
import com.svedprint.main.services.decorators.StudentDtoDecorator;
import com.svedprint.main.services.helpers.OptionalBooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class StudentDtoService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final SchoolClassDtoService schoolClassDtoService;
    private final SubjectOrientationDtoService subjectOrientationDtoService;
    private final TeacherDtoService teacherDtoService;

    public StudentDtoService(StudentRepository studentRepository,
                             StudentMapper studentMapper,
                             SchoolClassDtoService schoolClassDtoService,
                             SubjectOrientationDtoService subjectOrientationDtoService,
                             TeacherDtoService teacherDtoService) {

        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.schoolClassDtoService = schoolClassDtoService;
        this.subjectOrientationDtoService = subjectOrientationDtoService;
        this.teacherDtoService = teacherDtoService;

    }

    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents(String token) {
        return teacherDtoService.findEntityByToken(token).getSchoolClass().getStudents()
                .stream().map(studentMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentDto findOne(String id) {
        return studentRepository.findById(id).map(studentMapper::toDto).orElseThrow(() ->
                new SvedPrintException(SvedPrintExceptionType.MISSING_STUDENT));
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
            student.setSchoolClass(schoolClassDtoService.findEntity(classId));
        }

        getDecoratorNumber(teacher.getSchoolClass().getStudents(), student, dto); // TODO: Might still be bugged when transferring student from class to class if "number" is the same
        String subjectOrientationId = ofNullable(dto.getSubjectOrientation()).map(SubjectOrientationDto::getId)
                .orElse(ofNullable(student.getSubjectOrientation()).map(SubjectOrientation::getId).orElse(null));
        if (subjectOrientationId == null) { // TODO: Rework it so that it searches in the orientations of the class
            throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
        } else {
            SubjectOrientation subjectOrientation = subjectOrientationDtoService.findEntityById(subjectOrientationId);
            if (teacher.getSchoolClass().getSubjectOrientations().contains(subjectOrientation)) {
                if (!subjectOrientationId.equals(student.getSubjectOrientation().getId())) {
                    // TODO: Add grades handler for changing subjectOrientation (This works only if subjectOrientation update is separate)
                    handleSubjectOrientationGrades(dto, student, subjectOrientation);
                    //
                    student.setSubjectOrientation(subjectOrientation);
                }
            } else {
                throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCIONALITY);
            }
        }

        StudentDtoDecorator decorator = StudentDtoDecorator.builder().build();
        studentMapper.decorate(dto, decorator);
        studentMapper.updateEntity(decorator.init(student, update), student);
        return studentMapper.toDto(studentRepository.save(student));
    }


    private void handleSubjectOrientationGrades(StudentDto dto, Student entity, SubjectOrientation newSubjectOrientation) {
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
                entityGrades.add(idxSubject, 0);
            }
        }

        dto.setGrades(entityGrades);
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
            student.setSchoolClass(schoolClassDtoService.findEntity(classId));
        }

        String subjectOrientationId = ofNullable(dto.getSubjectOrientation()).map(SubjectOrientationDto::getId)
                .orElse(ofNullable(student.getSubjectOrientation()).map(SubjectOrientation::getId).orElse(null));
        if (subjectOrientationId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
        } else {
            student.setSubjectOrientation(subjectOrientationDtoService.findEntityById(subjectOrientationId));
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

    @Transactional(readOnly = true)
    public PageResponse<StudentDto> findAll(StudentDto dto) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, "number");
        Page<Student> page = dto != null ?
                studentRepository.findAll(makeFilter(dto), pageable) : Page.empty(pageable);

        List<StudentDto> content = page.getContent().stream()
                .map(studentMapper::toDtoInitial).collect(Collectors.toList());

        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    private BooleanExpression makeFilter(StudentDto dto) {
        QStudent qStudent = QStudent.student;
        OptionalBooleanBuilder opBuilder = OptionalBooleanBuilder.builder(qStudent.isNotNull());
        if (dto == null) {
            return opBuilder.build();
        }
        return opBuilder.notEmptyAnd(qStudent.schoolClass.id::eq, dto.getSchoolClass().getId()).build();
    }
}
