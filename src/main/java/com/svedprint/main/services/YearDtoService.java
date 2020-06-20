package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.YearMapper;
import com.svedprint.main.models.School;
import com.svedprint.main.models.Year;
import com.svedprint.main.repositories.SchoolRepository;
import com.svedprint.main.repositories.YearRepository;
import com.svedprint.main.services.decorators.YearDtoDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Optional.ofNullable;

@Service
public class YearDtoService {

    @Autowired
    private YearMapper yearMapper;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public YearDto update(YearDto yearDto, boolean update) {
        if (yearDto == null) {
            return null;
        }

        if ((yearDto.isIdSet() && !update) || (!yearDto.isIdSet() && update)) {
            throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCIONALITY);
        }

        final Year year = yearDto.isIdSet() ? yearRepository.getOne(yearDto.getId()) : new Year();

        if (year.getId() == null) {
            year.setClasses(new ArrayList<>());
            year.setSubjectOrientations(new ArrayList<>());
        } else {
            year.setClasses(null);
            year.setSubjectOrientations(null);
        }

        String schoolId = ofNullable(yearDto.getSchool()).map(SchoolDto::getId).orElse(ofNullable(year.getSchool()).map(School::getId).orElse(null));

        if (schoolId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_SCHOOL_ASSIGNED);
        } else if (!schoolId.equals(year.getSchool().getId())) {
            year.setSchool(schoolRepository.getOne(schoolId));
            yearDto.setSchool(null);
        }

        YearDtoDecorator decorator = YearDtoDecorator.builder().build();
        yearMapper.decorate(yearDto, decorator);
        yearMapper.updateEntity(decorator.init(year, update), year);
        return yearMapper.toDto(yearRepository.save(year));
    }

}
