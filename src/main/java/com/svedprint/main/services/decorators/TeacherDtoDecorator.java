package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.TeacherDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class TeacherDtoDecorator extends TeacherDto {

    public TeacherDto init() {
        return this;
    }

}
