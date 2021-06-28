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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Service
public class TeacherDtoService {

	private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

	private final TeacherRepository teacherRepository;
	private final TeacherMapper teacherMapper;

	private final SchoolDtoService schoolDtoService;
	private final SchoolClassDtoService schoolClassDtoService;

	public TeacherDtoService(TeacherRepository teacherRepository, TeacherMapper teacherMapper,
							 SchoolDtoService schoolDtoService, SchoolClassDtoService schoolClassDtoService) {
		this.teacherRepository = teacherRepository;
		this.teacherMapper = teacherMapper;
		this.schoolDtoService = schoolDtoService;
		this.schoolClassDtoService = schoolClassDtoService;
	}


	@Transactional(readOnly = true)
	public TeacherDto findOne(String id, String token) {
		// TODO: Rework with OAuth 2.0
		return teacherMapper.toDtoEager(teacherRepository.findByIdOrToken(id, token)
				.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.MISSING_USER)));
	}

	@Transactional(readOnly = true)
	public TeacherDto findOne(String id) {
		return teacherMapper.toDtoEager(teacherRepository.findById(id)
				.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.MISSING_USER)));
	}

	@Transactional(readOnly = true)
	public Teacher findEntityByToken(String token) {
		// TODO: Rework with OAuth 2.0
		return teacherRepository.findByToken(token)
				.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.MISSING_USER));
	}

	@Transactional
	public TeacherDto login(TeacherDto teacherDto, String password) {
		// TODO: Implement for OAuth 2.0
		Teacher teacher = teacherRepository.findByUsername(teacherDto.getUsername());
		if (argon2.verify(teacher.getPassword(), password)) {
			if (teacher.getSchoolClass() == null) {
				throw new SvedPrintException(SvedPrintExceptionType.NO_CLASS_ASSIGNED);
			}
			teacher.setToken(RandomStringUtils.randomAlphanumeric(20));
			teacherRepository.save(teacher);
			return teacherMapper.toDtoEager(teacher);
		}

		return null;
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

		if (teacherDto.getPassword() != null && !teacherDto.getPassword().isEmpty()) {
			teacherDto.setPassword(argon2.hash(4, 5000, 2, teacherDto.getPassword()));
		}

		TeacherDtoDecorator decorator = TeacherDtoDecorator.builder().build();
		teacherMapper.decorate(teacherDto, decorator);
		teacherMapper.updateEntity(decorator.init(teacher), teacher);

		return teacherMapper.toDtoEager(teacherRepository.save(teacher));
	}
}
