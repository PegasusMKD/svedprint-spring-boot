package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.mappers.SchoolMapper;
import com.svedprint.main.models.School;
import com.svedprint.main.repositories.SchoolRepository;
import com.svedprint.main.services.decorators.SchoolDtoDecorator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class SchoolDtoService {

	private final SchoolRepository schoolRepository;
	private final SchoolMapper schoolMapper;

	public SchoolDtoService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
		this.schoolRepository = schoolRepository;
		this.schoolMapper = schoolMapper;
	}

	@Transactional(readOnly = true)
	public School findEntityById(String schoolId) {
		return schoolRepository.getOne(schoolId);
	}

	@Transactional(readOnly = true)
	public SchoolDto findOne(String id) {
		return schoolMapper.toDto(schoolRepository.getOne(id));
	}

	@Transactional
	public SchoolDto save(SchoolDto schoolDto) {
		if (schoolDto == null) {
			return null;
		}
		final School school = schoolDto.isIdSet() ? schoolRepository.getOne(schoolDto.getId()) : new School();
		SchoolDtoDecorator decorator = SchoolDtoDecorator.builder().build();
		schoolMapper.decorate(schoolDto, decorator);
		schoolMapper.updateEntity(decorator.init(school), school);
		return schoolMapper.toDto(schoolRepository.save(school));
	}
}
