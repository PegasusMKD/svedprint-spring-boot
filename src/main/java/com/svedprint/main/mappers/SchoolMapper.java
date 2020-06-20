package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.models.School;
import com.svedprint.main.services.decorators.SchoolDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

// TODO: Specify other mappers needed when implementing this mapper (as u write the code)

// , uses = {YearMapper.class, TeacherMapper.class}
@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SchoolMapper {
    School toEntity(SchoolDto dto);

    SchoolDto toDto(School entity);

    void updateEntity(SchoolDto dto, @MappingTarget School entity);

    void decorate(SchoolDto dto, @MappingTarget SchoolDtoDecorator decorator);
}
