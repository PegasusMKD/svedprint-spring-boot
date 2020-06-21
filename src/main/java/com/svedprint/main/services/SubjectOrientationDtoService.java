package com.svedprint.main.services;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.SubjectOrientationMapper;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.models.Year;
import com.svedprint.main.repositories.SubjectOrientationRepository;
import com.svedprint.main.repositories.YearRepository;
import com.svedprint.main.services.decorators.SubjectOrientationDtoDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Optional.ofNullable;

@Service
public class SubjectOrientationDtoService {

    @Autowired
    private SubjectOrientationRepository subjectOrientationRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private SubjectOrientationMapper subjectOrientationMapper;


    public SubjectOrientationDto save(SubjectOrientationDto subjectOrientationDto, boolean update) {
        if (subjectOrientationDto == null) {
            return null;
        }

        final SubjectOrientation subjectOrientation = subjectOrientationDto.isIdSet() ? subjectOrientationRepository.getOne(subjectOrientationDto.getId()) : new SubjectOrientation();

        String yearId = ofNullable(subjectOrientationDto.getYear()).map(YearDto::getId).orElse(ofNullable(subjectOrientation.getYear()).map(Year::getId).orElse(null));
        if (yearId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_YEAR_PROVIDED);
        } else {
            subjectOrientation.setYear(yearRepository.getOne(yearId));
        }

        if (subjectOrientation.getId() == null) {
            subjectOrientation.setClasses(new ArrayList<>());
            subjectOrientation.setStudents(new ArrayList<>());
        } else {
            subjectOrientation.setStudents(null);
            subjectOrientation.setClasses(null);
        }

        SubjectOrientationDtoDecorator decorator = SubjectOrientationDtoDecorator.builder().build();
        subjectOrientationMapper.decorate(subjectOrientationDto, decorator);
        subjectOrientationMapper.updateEntity(decorator.init(subjectOrientation, update), subjectOrientation);
        return subjectOrientationMapper.toDto(subjectOrientationRepository.save(subjectOrientation));
    }

}
