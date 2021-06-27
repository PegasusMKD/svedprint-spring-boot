package com.svedprint.main.services;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.SubjectOrientationMapper;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.models.Teacher;
import com.svedprint.main.models.Year;
import com.svedprint.main.repositories.SubjectOrientationRepository;
import com.svedprint.main.services.decorators.SubjectOrientationDtoDecorator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class SubjectOrientationDtoService {

	private final SubjectOrientationRepository subjectOrientationRepository;
	private final SubjectOrientationMapper subjectOrientationMapper;

	private final TeacherDtoService teacherDtoService;
	private final YearDtoService yearDtoService;

	public SubjectOrientationDtoService(SubjectOrientationRepository subjectOrientationRepository,
										SubjectOrientationMapper subjectOrientationMapper,
										TeacherDtoService teacherDtoService, YearDtoService yearDtoService) {
		this.subjectOrientationRepository = subjectOrientationRepository;
		this.subjectOrientationMapper = subjectOrientationMapper;
		this.teacherDtoService = teacherDtoService;
		this.yearDtoService = yearDtoService;
	}

	@Transactional(readOnly = true)
	public SubjectOrientation findEntityById(String subjectOrientationId) {
		return subjectOrientationRepository.getOne(subjectOrientationId);
	}

	// TODO: Possibly remove clone functionalities
	@Transactional
	public SubjectOrientationDto save(SubjectOrientationDto subjectOrientationDto, String token, boolean update) {
		if (subjectOrientationDto == null) {
			return null;
		}
		if (!update && subjectOrientationDto.getShortName() == null) {
			throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
		}

		// TODO: Implement with OAuth 2.0
		final Teacher teacher = teacherDtoService.findEntityByToken(token);

		// TODO: Research why we search by short-name & year instead of just ID (most probably to check if a similar copy exists so we can clone it)
		List<SubjectOrientation> subjectOrientations = subjectOrientationRepository.findAllByShortNameAndYear(subjectOrientationDto.getShortName(), teacher.getSchoolClass().getYear());
		SubjectOrientation subjectOrientation = !update ? !subjectOrientations.isEmpty() ? subjectOrientations.get(0) : new SubjectOrientation() :
				subjectOrientationRepository.findByShortNameAndClasses(subjectOrientationDto.getShortName(), teacher.getSchoolClass());

		if (subjectOrientation == null) {
			throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
		}

		// Two orientations with the same short name, in the same class, is not allowed
		if (!update && teacher.getSchoolClass().getSubjectOrientations().stream()
				.map(SubjectOrientation::getShortName).collect(Collectors.toList()).contains(subjectOrientation.getShortName())) {
			throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCTIONALITY);
		}

		if (subjectOrientation.getId() != null && !teacher.getSchoolClass().getSubjectOrientations().stream()
				.map(SubjectOrientation::getShortName).collect(Collectors.toList()).contains(subjectOrientation.getShortName())) {
			subjectOrientation = clone(subjectOrientation);
			if (subjectOrientationDto.getSubjects() == null || subjectOrientationDto.getSubjects().isEmpty()) {
				subjectOrientationDto.setSubjects(subjectOrientation.getSubjects());
			}
		}
		subjectOrientation.setYear(teacher.getSchoolClass().getYear());
		subjectOrientation.setClasses(teacher.getSchoolClass());

		SubjectOrientationDtoDecorator decorator = SubjectOrientationDtoDecorator.builder().build();
		subjectOrientationMapper.decorate(subjectOrientationDto, decorator);
		subjectOrientationMapper.updateEntity(decorator.init(subjectOrientation), subjectOrientation);
		return subjectOrientationMapper.toDto(subjectOrientationRepository.save(subjectOrientation));
	}

	private SubjectOrientation clone(SubjectOrientation source) {
		SubjectOrientationDto dto = subjectOrientationMapper.toDto(source);
		dto.setId(null);
		return subjectOrientationMapper.toEntity(dto);
	}

	@Deprecated
	@Transactional
	public SubjectOrientationDto oldSave(SubjectOrientationDto subjectOrientationDto) {
		if (subjectOrientationDto == null) {
			return null;
		}

		final SubjectOrientation subjectOrientation = subjectOrientationDto.isIdSet() ? subjectOrientationRepository.getOne(subjectOrientationDto.getId()) : new SubjectOrientation();

		String yearId = ofNullable(subjectOrientationDto.getYear()).map(YearDto::getId).orElse(ofNullable(subjectOrientation.getYear()).map(Year::getId).orElse(null));
		if (yearId == null) {
			throw new SvedPrintException(SvedPrintExceptionType.NO_YEAR_PROVIDED);
		} else {
			subjectOrientation.setYear(yearDtoService.findEntityById(yearId));
		}

		if (subjectOrientation.getId() == null) {
			subjectOrientation.setStudents(new ArrayList<>());
		} else {
			subjectOrientation.setStudents(null);
			subjectOrientation.setClasses(null);
		}

		SubjectOrientationDtoDecorator decorator = SubjectOrientationDtoDecorator.builder().build();
		subjectOrientationMapper.decorate(subjectOrientationDto, decorator);
		subjectOrientationMapper.updateEntity(decorator.init(subjectOrientation), subjectOrientation);
		return subjectOrientationMapper.toDto(subjectOrientationRepository.save(subjectOrientation));
	}

	@Deprecated
	@Transactional(readOnly = true)
	public List<SubjectOrientationDto> get(String token) {
		// TODO: Implement with OAuth 2.0
		return teacherDtoService.findEntityByToken(token).getSchoolClass().getSubjectOrientations().stream()
				.map(subjectOrientationMapper::toDto).collect(Collectors.toList());
	}

	@Transactional
	public boolean delete(SubjectOrientationDto subjectOrientationDto, String token) {
		try {
			// TODO: Implement with OAuth 2.0
			Teacher teacher = teacherDtoService.findEntityByToken(token);
			if (!subjectOrientationDto.isIdSet()) {
				throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
			}
			SubjectOrientation subjectOrientation = subjectOrientationRepository.getOne(subjectOrientationDto.getId());
			if (!teacher.getSchoolClass().getSubjectOrientations().contains(subjectOrientation)) {
				throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCTIONALITY);
			}
			subjectOrientationRepository.delete(subjectOrientation);
			return true;
		} catch (Exception e) {
			// TODO: Add logger here
			return false;
		}
	}
}
