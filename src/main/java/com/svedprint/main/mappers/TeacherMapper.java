package com.svedprint.main.mappers;

import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.models.Teacher;
import com.svedprint.main.services.decorators.TeacherDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = SchoolMapper.class)
public interface TeacherMapper {
    Teacher toEntity(TeacherDto dto);

    TeacherDto toDto(Teacher entity);

    void updateEntity(TeacherDto dto, @MappingTarget Teacher entity);

    void decorate(TeacherDto dto, @MappingTarget TeacherDtoDecorator decorator);
}
