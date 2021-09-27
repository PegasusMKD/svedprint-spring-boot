package com.svedprint.main.services;

import com.svedprint.main.configs.oauth.security.UserContext;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Service
public class TeacherDtoService {

	private final PasswordEncoder argon2;

	private final TeacherRepository teacherRepository;
	private final TeacherMapper teacherMapper;

	private final SchoolDtoService schoolDtoService;
	private final SchoolClassDtoService schoolClassDtoService;

	public TeacherDtoService(PasswordEncoder argon2, TeacherRepository teacherRepository, TeacherMapper teacherMapper,
							 SchoolDtoService schoolDtoService, SchoolClassDtoService schoolClassDtoService) {
		this.argon2 = argon2;
		this.teacherRepository = teacherRepository;
		this.teacherMapper = teacherMapper;
		this.schoolDtoService = schoolDtoService;
		this.schoolClassDtoService = schoolClassDtoService;
	}

	@Transactional(readOnly = true)
	public TeacherDto findOne(String id) {
		return teacherMapper.toDtoEager(teacherRepository.findById(id)
				.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.MISSING_USER)));
	}

	@Transactional(readOnly = true)
	public TeacherDto findByUsername(String username) {
		return teacherMapper.toDtoEager(teacherRepository.findByUsername(username)
				.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.MISSING_USER)));
	}

	@Transactional(readOnly = true)
	public Teacher findEntityByToken() {
		return teacherRepository.findByUsername(UserContext.getUsername())
				.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.MISSING_USER));
	}

	@Transactional
	public TeacherDto save(TeacherDto teacherDto) {
		if (teacherDto == null) {
			return null;
		}
		final Teacher teacher = teacherDto.isIdSet() ? teacherRepository.getOne(teacherDto.getId()) : new Teacher();

		// TODO: Add admin check for whether update is coming from same school,
		//  or different one with checking whether the admin has a school, and if he does,
		//  whether it's the same as the teacher

		String school = ofNullable(teacherDto.getSchool()).map(SchoolDto::getId)
				.orElseGet(() -> ofNullable(teacher.getSchool()).map(School::getId)
						.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.NO_SCHOOL_ASSIGNED)));
		teacherDto.setSchool(schoolDtoService.findOne(school));

		String schoolClass = ofNullable(teacherDto.getSchoolClass()).map(SchoolClassDto::getId)
				.orElseGet(() -> ofNullable(teacher.getSchoolClass()).map(SchoolClass::getId)
						.orElseThrow(() -> new AssertionError(SvedPrintExceptionType.NO_CLASS_ASSIGNED)));
		teacher.setSchoolClass(schoolClassDtoService.findEntity(schoolClass));

		TeacherDtoDecorator decorator = TeacherDtoDecorator.builder().build();
		teacherMapper.decorate(teacherDto, decorator);

		TeacherDto tmpTeacher = decorator.init(teacher);
		if (tmpTeacher.getPassword() != null && !tmpTeacher.getPassword().isEmpty()) {
			tmpTeacher.setPassword(argon2.encode(tmpTeacher.getPassword()));
		}

		teacherMapper.updateEntity(tmpTeacher, teacher);

		return teacherMapper.toDtoEager(teacherRepository.save(teacher));
	}
}
