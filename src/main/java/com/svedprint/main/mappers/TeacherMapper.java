package com.svedprint.main.mappers;

import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.models.Teacher;
import com.svedprint.main.services.decorators.TeacherDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

// TODO: Specify other mappers needed when implementing this mapper (as u write the code)

// , uses = { SchoolMapper.class, SchoolClassMapper.class }
@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {SchoolMapper.class, YearMapper.class, StudentMapper.class, SchoolClassMapper.class})
public interface TeacherMapper {

    @Mapping(target = "password", ignore = true)
    Teacher toEntity(TeacherDto dto);

    @Mapping(target = "password", ignore = true)
    TeacherDto toDto(Teacher entity);

    void updateEntity(TeacherDto dto, @MappingTarget Teacher entity);

    void decorate(TeacherDto dto, @MappingTarget TeacherDtoDecorator decorator);
}
