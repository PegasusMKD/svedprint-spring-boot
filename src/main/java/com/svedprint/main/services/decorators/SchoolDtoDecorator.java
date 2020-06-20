package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SchoolDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SchoolDtoDecorator extends SchoolDto {

    public SchoolDto init() {
        return this;
    }
}
