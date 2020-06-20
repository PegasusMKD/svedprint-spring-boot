package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.mappers.SchoolMapper;
import com.svedprint.main.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper schoolMapper;

    public SchoolDto findOne(String id) {
        return schoolMapper.toDto(schoolRepository.getOne(id));
    }
}
