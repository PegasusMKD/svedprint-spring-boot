package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.services.decorators.SchoolClassDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

// TODO: Specify other mappers needed when implementing this mapper (as u write the code)

// , uses = {SubjectOrientationMapper.class, TeacherMapper.class, StudentMapper.class, YearMapper.class}
@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {SchoolMapper.class, YearMapper.class, SubjectOrientationMapper.class})
public interface SchoolClassMapper {
    @Mapping(target = "students", ignore = true)
    SchoolClass toEntity(SchoolClassDto dto);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "students", ignore = true)
    SchoolClassDto toDto(SchoolClass entity);

    void updateEntity(SchoolClassDto dto, @MappingTarget SchoolClass entity);

    void decorate(SchoolClassDto dto, @MappingTarget SchoolClassDtoDecorator decorator);
}
