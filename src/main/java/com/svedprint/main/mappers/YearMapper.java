package com.svedprint.main.mappers;

import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.models.Year;
import com.svedprint.main.services.decorators.YearDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		uses = {SchoolMapper.class, SchoolClassMapper.class, SubjectOrientationMapper.class})
public interface YearMapper {
	Year toEntity(YearDto dto);

	@Mapping(target = "classes", ignore = true)
	YearDto toDto(Year entity);

	@Mapping(target = "school", ignore = true)
	@Mapping(target = "classes", ignore = true)
	@Mapping(target = "subjectOrientations", ignore = true)
	void updateEntity(YearDto dto, @MappingTarget Year entity);

	void decorate(YearDto dto, @MappingTarget YearDtoDecorator decorator);
}
