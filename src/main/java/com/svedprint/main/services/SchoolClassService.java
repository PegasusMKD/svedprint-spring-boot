package com.svedprint.main.services;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.mappers.SchoolClassMapper;
import com.svedprint.main.repositories.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolClassMapper schoolClassMapper;

    public SchoolClassDto findOne(String id) {
        return schoolClassMapper.toDto(schoolClassRepository.getOne(id));
    }

}
