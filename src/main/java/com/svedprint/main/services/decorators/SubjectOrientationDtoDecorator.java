package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.models.SubjectOrientation;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SubjectOrientationDtoDecorator extends SubjectOrientationDto {

    private static <T> List<T> ofNullableList(List<T> elements, List<T> entityValues, SubjectOrientation entity) {
        if (elements != null && elements.isEmpty()) {
            if (entity.getSubjects().isEmpty()) {
                return new ArrayList<>();
            } else {
                return entityValues;
            }
        }
        return elements;
    }

    public SubjectOrientationDto init(SubjectOrientation entity, boolean update) {
        shortName = ofNullable(shortName).orElse(ofNullable(entity.getShortName()).orElse("Име"));
        fullName = ofNullable(fullName).orElse(ofNullable(fullName).orElse("Целосно име"));
        shortNames = ofNullable(shortNames).orElse(ofNullable(shortNames).orElse(new ArrayList<>()));
        subjects = ofNullableList(subjects, entity.getSubjects(), entity);
        return this;
    }

}
