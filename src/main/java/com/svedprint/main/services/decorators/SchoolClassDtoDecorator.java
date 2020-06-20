package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SchoolClassDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SchoolClassDtoDecorator extends SchoolClassDto {

    public SchoolClassDto init() {
        return this;
    }

}
