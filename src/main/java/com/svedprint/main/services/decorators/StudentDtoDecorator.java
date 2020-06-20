package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.StudentDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class StudentDtoDecorator extends StudentDto {

    public StudentDto init() {
        return this;
    }
}
