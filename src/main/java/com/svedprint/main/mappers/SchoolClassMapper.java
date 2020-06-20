package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.services.decorators.SchoolClassDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = SchoolMapper.class)
public interface SchoolClassMapper {
    SchoolClass toEntity(SchoolClassDto dto);

    SchoolClassDto toDto(SchoolClass entity);

    void updateEntity(SchoolClassDto dto, @MappingTarget SchoolClass entity);

    void decorate(SchoolClassDto dto, @MappingTarget SchoolClassDtoDecorator decorator);
}
