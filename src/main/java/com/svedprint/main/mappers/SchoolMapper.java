package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.models.School;
import com.svedprint.main.services.decorators.SchoolDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

// TODO: Specify other mappers needed when implementing this mapper (as u write the code)

// , uses = {YearMapper.class, TeacherMapper.class}
@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SchoolMapper {
    School toEntity(SchoolDto dto);

    @Mapping(target = "years", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    SchoolDto toDto(School entity);

    @Mapping(target = "years", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    void updateEntity(SchoolDto dto, @MappingTarget School entity);

    void decorate(SchoolDto dto, @MappingTarget SchoolDtoDecorator decorator);
}
