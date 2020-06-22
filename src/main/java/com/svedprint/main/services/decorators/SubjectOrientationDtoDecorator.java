package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.models.SubjectOrientation;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SubjectOrientationDtoDecorator extends SubjectOrientationDto {

    public SubjectOrientationDto init(SubjectOrientation entity, boolean update) {
        shortName = ofNullable(shortName).orElse(ofNullable(entity.getShortName()).orElse("Име"));
        fullName = ofNullable(fullName).orElse(ofNullable(fullName).orElse("Целосно име"));
        shortNames = ofNullable(shortNames).orElse(ofNullable(shortNames).orElse(new ArrayList<>()));
        subjects = ofNullable(subjects).orElse(ofNullable(entity.getSubjects()).orElse(new ArrayList<>()));
        System.out.println(subjects);
        return this;
    }

}
