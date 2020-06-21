package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.TeacherMapper;
import com.svedprint.main.models.School;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.Teacher;
import com.svedprint.main.repositories.TeacherRepository;
import com.svedprint.main.services.decorators.TeacherDtoDecorator;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class TeacherDtoService {

    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private SchoolDtoService schoolDtoService;

    @Autowired
    private SchoolClassDtoService schoolClassDtoService;


    public TeacherDto findOne(String id, String token) {
        return teacherMapper.toDto(teacherRepository.findByIdOrToken(id, token));
    }

    public Teacher findEntityByToken(String token) {
        return teacherRepository.findByToken(token);
    }

    public TeacherDto login(TeacherDto teacherDto, String password) {
        Teacher teacher = teacherRepository.findByUsername(teacherDto.getUsername());
        if (argon2.verify(teacher.getPassword(), password)) {
            if (teacher.getSchoolClass() == null) {
                throw new SvedPrintException(SvedPrintExceptionType.NO_CLASS_ASSIGNED);
            }
            teacher.setToken(RandomStringUtils.randomAlphanumeric(20));
            teacherRepository.save(teacher);
            return teacherMapper.toDto(teacher);
        }

        return null;
    }

    public TeacherDto save(TeacherDto teacherDto, boolean update) {
        if (teacherDto == null) {
            return null;
        }
        final Teacher teacher = teacherDto.isIdSet() ? teacherRepository.getOne(teacherDto.getId()) : new Teacher();

        // TODO: Add admin check for whether update is coming from same school, or different one with checking whether the admin has a school, and if he does, whether it's the same as the teacher

        String school = ofNullable(teacherDto.getSchool()).map(SchoolDto::getId).orElse(ofNullable(teacher.getSchool()).map(School::getId).orElse(null));
        if (school == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_SCHOOL_ASSIGNED);
        } else {
            teacherDto.setSchool(schoolDtoService.findOne(school));
        }

        String schoolClass = ofNullable(teacherDto.getSchoolClass()).map(SchoolClassDto::getId).orElse(ofNullable(teacher.getSchoolClass()).map(SchoolClass::getId).orElse(null));
        if (schoolClass == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_CLASS_ASSIGNED);
        } else {
            System.out.println("Gets called!");
            teacher.setSchoolClass(schoolClassDtoService.findEntity(schoolClass));
        }

        TeacherDtoDecorator decorator = TeacherDtoDecorator.builder().build();
        teacherMapper.decorate(teacherDto, decorator);
        teacherMapper.updateEntity(decorator.init(teacher, update, argon2), teacher);

        return teacherMapper.toDto(teacherRepository.save(teacher));
    }
}
