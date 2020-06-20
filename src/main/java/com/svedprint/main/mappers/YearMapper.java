package com.svedprint.main.mappers;

import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.models.Year;
import com.svedprint.main.services.decorators.YearDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = SchoolMapper.class)
public interface YearMapper {
    Year toEntity(YearDto dto);

    YearDto toDto(Year entity);

    void updateEntity(YearDto dto, @MappingTarget Year entity);

    void decorate(YearDto dto, @MappingTarget YearDtoDecorator decorator);
}
