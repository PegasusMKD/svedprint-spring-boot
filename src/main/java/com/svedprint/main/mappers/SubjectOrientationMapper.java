package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.services.decorators.SubjectOrientationDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		uses = {YearMapper.class, SchoolClassMapper.class, StudentMapper.class})
public interface SubjectOrientationMapper {
	SubjectOrientation toEntity(SubjectOrientationDto dto);

	@Mapping(target = "year", ignore = true)
	@Mapping(target = "classes", ignore = true)
	@Mapping(target = "students", ignore = true)
	SubjectOrientationDto toDto(SubjectOrientation entity);

	@Mapping(target = "classes", ignore = true)
	void updateEntity(SubjectOrientationDto dto, @MappingTarget SubjectOrientation entity);

	void decorate(SubjectOrientationDto dto, @MappingTarget SubjectOrientationDtoDecorator decorator);
}
