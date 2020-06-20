package com.svedprint.main.mappers;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.services.decorators.SubjectOrientationDtoDecorator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = SchoolMapper.class)
public interface SubjectOrientationMapper {
    SubjectOrientation toEntity(SubjectOrientationDto dto);

    SubjectOrientationDto toDto(SubjectOrientation entity);

    void updateEntity(SubjectOrientationDto dto, @MappingTarget SubjectOrientation entity);

    void decorate(SubjectOrientationDto dto, @MappingTarget SubjectOrientationDtoDecorator decorator);
}
