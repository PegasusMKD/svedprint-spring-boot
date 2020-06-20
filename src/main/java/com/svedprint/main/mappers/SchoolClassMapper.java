package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.services.decorators.SchoolClassDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

// TODO: Specify other mappers needed when implementing this mapper (as u write the code)

// , uses = {SubjectOrientationMapper.class, TeacherMapper.class, StudentMapper.class, YearMapper.class}
@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SchoolClassMapper {
    SchoolClass toEntity(SchoolClassDto dto);

    SchoolClassDto toDto(SchoolClass entity);

    void updateEntity(SchoolClassDto dto, @MappingTarget SchoolClass entity);

    void decorate(SchoolClassDto dto, @MappingTarget SchoolClassDtoDecorator decorator);
}
