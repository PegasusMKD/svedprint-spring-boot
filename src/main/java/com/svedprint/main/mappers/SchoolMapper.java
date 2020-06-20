package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.models.School;
import com.svedprint.main.services.decorators.SchoolDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = SchoolMapper.class)
public interface SchoolMapper {
    School toEntity(SchoolDto dto);

    SchoolDto toDto(School entity);

    void updateEntity(SchoolDto dto, @MappingTarget School entity);

    void decorate(SchoolDto dto, @MappingTarget SchoolDtoDecorator decorator);
}
