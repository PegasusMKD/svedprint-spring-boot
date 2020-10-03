package com.svedprint.main.mappers;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.models.Student;
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
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {SubjectOrientationMapper.class})
public interface TeacherMapper {

    @Mapping(target = "password", ignore = true)
    Teacher toEntity(TeacherDto dto);

    @Mapping(target = "school.years", ignore = true)
    @Mapping(target = "school.teachers", ignore = true)
    @Mapping(target = "schoolClass.teacher", ignore = true)
    @Mapping(target = "schoolClass.students", ignore = true)
    @Mapping(target = "schoolClass.year.school", ignore = true)
    @Mapping(target = "schoolClass.year.classes", ignore = true)
    @Mapping(target = "schoolClass.year.subjectOrientations", ignore = true)
    @Mapping(target = "password", ignore = true)
    TeacherDto toDto(Teacher entity);

    void updateEntity(TeacherDto dto, @MappingTarget Teacher entity);

    void decorate(TeacherDto dto, @MappingTarget TeacherDtoDecorator decorator);

}
