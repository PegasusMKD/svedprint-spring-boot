package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SubjectOrientationDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SubjectOrientationDtoDecorator extends SubjectOrientationDto {

    public SubjectOrientationDto init() {
        return this;
    }

}
