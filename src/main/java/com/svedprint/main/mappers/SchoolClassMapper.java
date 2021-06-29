package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.services.decorators.SchoolClassDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		uses = {SchoolMapper.class, YearMapper.class, TeacherMapper.class, StudentMapper.class, SubjectOrientationMapper.class})
public interface SchoolClassMapper {
	@Mapping(target = "students", ignore = true)
	SchoolClass toEntity(SchoolClassDto dto);

	@Mapping(target = "teacher", ignore = true)
	@Mapping(target = "students", ignore = true)
	SchoolClassDto toDto(SchoolClass entity);

	@Mapping(target = "students", ignore = true)
	void updateEntity(SchoolClassDto dto, @MappingTarget SchoolClass entity);

	void decorate(SchoolClassDto dto, @MappingTarget SchoolClassDtoDecorator decorator);
}
