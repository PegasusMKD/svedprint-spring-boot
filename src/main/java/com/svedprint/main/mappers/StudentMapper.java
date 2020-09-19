package com.svedprint.main.mappers;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.models.Student;
import com.svedprint.main.services.decorators.StudentDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

// TODO: Specify other mappers needed when implementing this mapper (as u write the code)

// , uses = {SchoolClassMapper.class, SubjectOrientationMapper.class}
@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {SchoolMapper.class, YearMapper.class, SubjectOrientationMapper.class})
public interface StudentMapper {
    Student toEntity(StudentDto dto);

    @Mapping(target = "schoolClass", ignore = true)
    @Mapping(target = "maturska", ignore = true)
    StudentDto toDto(Student entity);

    void updateEntity(StudentDto dto, @MappingTarget Student entity);

    void decorate(StudentDto dto, @MappingTarget StudentDtoDecorator decorator);
}
