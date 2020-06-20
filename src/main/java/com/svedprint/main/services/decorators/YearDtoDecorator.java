package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.YearDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class YearDtoDecorator extends YearDto {
    public YearDto init() {
        return this;
    }
}
