package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.SchoolClassMapper;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.Teacher;
import com.svedprint.main.models.Year;
import com.svedprint.main.repositories.SchoolClassRepository;
import com.svedprint.main.services.decorators.SchoolClassDtoDecorator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static java.util.Optional.ofNullable;

@Service
public class SchoolClassDtoService {

	private final SchoolClassRepository schoolClassRepository;
	private final SchoolClassMapper schoolClassMapper;

	private final YearDtoService yearDtoService;
	private final SubjectOrientationDtoService subjectOrientationDtoService;
	private final TeacherDtoService teacherDtoService;

	@Lazy
	public SchoolClassDtoService(SchoolClassRepository schoolClassRepository, SchoolClassMapper schoolClassMapper,
								 YearDtoService yearDtoService, SubjectOrientationDtoService subjectOrientationDtoService,
								 TeacherDtoService teacherDtoService) {
		this.schoolClassRepository = schoolClassRepository;
		this.schoolClassMapper = schoolClassMapper;
		this.yearDtoService = yearDtoService;
		this.subjectOrientationDtoService = subjectOrientationDtoService;
		this.teacherDtoService = teacherDtoService;
	}

	public SchoolClassDto findOne(String id) {
		return schoolClassMapper.toDto(schoolClassRepository.getOne(id));
	}

	@Transactional(readOnly = true)
	public SchoolClass findEntity(String id) {
		return schoolClassRepository.findById(id).orElseThrow(() ->
				new SvedPrintException(SvedPrintExceptionType.MISSING_CLASS_NAME));
	}

	@Transactional
	public SchoolClassDto save(SchoolClassDto schoolClassDto) {
		// TODO: Differentiate the operations between Admin and Teacher
		if (schoolClassDto == null) {
			return null;
		}

		final SchoolClass schoolClass = schoolClassDto.isIdSet() ? schoolClassRepository.getOne(schoolClassDto.getId()) : new SchoolClass();

		// TODO: May be removable
		ofNullable(schoolClassDto.getTeacher()).map(TeacherDto::getId)
				.ifPresent(teacherId -> schoolClassDto.setTeacher(teacherDtoService.findOne(teacherId)));

		SchoolClassDtoDecorator decorator = SchoolClassDtoDecorator.builder().build();
		schoolClassMapper.decorate(schoolClassDto, decorator);

		// TODO: Check if this iteration can be removed (might be properly covered by JPA and mappers)
		schoolClassDto.getSubjectOrientations().forEach(subjectOrientationDto -> schoolClass.getSubjectOrientations()
				.add(subjectOrientationDtoService.findEntityById(subjectOrientationDto.getId())));
		schoolClassDto.setSubjectOrientations(null);

		Year tmpYear = schoolClassDto.getYear().isIdSet() ? yearDtoService.findEntityById(schoolClassDto.getYear().getId()) : schoolClass.getYear();
		schoolClassMapper.updateEntity(decorator.init(schoolClass, tmpYear), schoolClass);
		return schoolClassMapper.toDto(schoolClassRepository.save(schoolClass));
	}

	@Transactional(readOnly = true)
	public SchoolClassDto getSchoolClassByUser(TeacherDto teacherDto) {
		Teacher teacher = teacherDtoService.findEntityByToken(teacherDto.getToken());
		return schoolClassMapper.toDto(teacher.getSchoolClass());
	}


}
