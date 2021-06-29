package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.YearMapper;
import com.svedprint.main.models.School;
import com.svedprint.main.models.Year;
import com.svedprint.main.repositories.YearRepository;
import com.svedprint.main.services.decorators.YearDtoDecorator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static java.util.Optional.ofNullable;

@Service
public class YearDtoService {

	private final YearMapper yearMapper;
	private final YearRepository yearRepository;

	private final SchoolDtoService schoolDtoService;

	public YearDtoService(YearMapper yearMapper, YearRepository yearRepository, SchoolDtoService schoolDtoService) {
		this.yearMapper = yearMapper;
		this.yearRepository = yearRepository;
		this.schoolDtoService = schoolDtoService;
	}

	@Transactional(readOnly = true)
	public Year findEntityById(String yearId) {
		return yearRepository.getOne(yearId);
	}

	@Transactional
	public YearDto save(YearDto yearDto) {
		if (yearDto == null) {
			return null;
		}

		final Year year = yearDto.isIdSet() ? yearRepository.findById(yearDto.getId())
				.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCTIONALITY)) : new Year();

		String schoolId = ofNullable(yearDto.getSchool()).map(SchoolDto::getId)
				.orElseGet(() -> ofNullable(year.getSchool()).map(School::getId)
						.orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.NO_SCHOOL_ASSIGNED)));
		year.setSchool(schoolDtoService.findEntityById(schoolId));

		YearDtoDecorator decorator = YearDtoDecorator.builder().build();
		yearMapper.decorate(yearDto, decorator);
		yearMapper.updateEntity(decorator.init(year), year);
		return yearMapper.toDto(yearRepository.save(year));
	}

}
