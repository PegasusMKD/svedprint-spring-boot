package com.svedprint.main.mappers;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.models.Student;
import com.svedprint.main.services.decorators.StudentDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = StudentMapper.class)
public interface StudentMapper {
    Student toEntity(StudentDto dto);

    StudentDto toDto(Student entity);

    void updateEntity(StudentDto dto, @MappingTarget Student entity);

    void decorate(StudentDto dto, @MappingTarget StudentDtoDecorator decorator);
}
