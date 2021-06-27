package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.models.Year;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
public class YearDtoDecorator extends YearDto {
	public YearDto init(Year entity) {
		name = ofNullable(name).orElseGet(() -> ofNullable(entity.getName()).orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.NO_YEAR_PROVIDED)));
		dateWhenTestimonyConfirmed = ofNullable(dateWhenTestimonyConfirmed).orElse(ofNullable(dateWhenTestimonyConfirmed).orElse(new Date()));
		return this;
	}
}
