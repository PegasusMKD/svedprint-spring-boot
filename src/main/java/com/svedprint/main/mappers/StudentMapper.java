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
public abstract class StudentMapper {
    public abstract Student toEntity(StudentDto dto);

    @Mapping(target = "schoolClass", ignore = true)
    @Mapping(target = "maturska", ignore = true)
    public abstract StudentDto toDto(Student entity);

    public abstract void updateEntity(StudentDto dto, @MappingTarget Student entity);

    public abstract void decorate(StudentDto dto, @MappingTarget StudentDtoDecorator decorator);

    public StudentDto toDtoInitial(Student entity) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(entity.getId());
        studentDto.setFirstName(entity.getFirstName());
        studentDto.setLastName(entity.getLastName());
        studentDto.setNumber(entity.getNumber());
        return studentDto;
    }
}
