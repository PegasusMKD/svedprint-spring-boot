package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.mappers.SchoolMapper;
import com.svedprint.main.models.School;
import com.svedprint.main.repositories.SchoolRepository;
import com.svedprint.main.services.decorators.SchoolDtoDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SchoolDtoService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper schoolMapper;

    public SchoolDto findOne(String id) {
        return schoolMapper.toDto(schoolRepository.getOne(id));
    }

    public SchoolDto save(SchoolDto schoolDto, boolean update) {
        System.out.println(schoolDto);
        if (schoolDto == null) {
            return null;
        }
        System.out.println("Got through the if...?");
        final School school = schoolDto.isIdSet() ? schoolRepository.getOne(schoolDto.getId()) : new School();
        System.out.println(school);
        if (school.getId() == null && !update) {
            school.setYears(new ArrayList<>());
            school.setTeachers(new ArrayList<>());
        }

        SchoolDtoDecorator decorator = SchoolDtoDecorator.builder().build();
        schoolMapper.decorate(schoolDto, decorator);
        schoolMapper.updateEntity(decorator.init(school, update), school);
        return schoolMapper.toDto(schoolRepository.save(school));
    }
}
