package com.svedprint.main.services.decorators;

import com.svedprint.main.models.SubjectOrientation;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SubjectOrientationDtoDecorator extends SubjectOrientation {

    public SubjectOrientation init() {
        return this;
    }

}
