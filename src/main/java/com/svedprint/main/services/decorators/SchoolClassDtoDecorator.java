package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SchoolClassDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.Year;
import com.svedprint.main.repositories.YearRepository;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
public class SchoolClassDtoDecorator extends SchoolClassDto {

	public SchoolClassDto init(SchoolClass entity, Year tmpYear) {
		name = ofNullable(name).orElseGet(() -> ofNullable(entity.getName()).orElse(getDefaultName(tmpYear)));
		entity.setYear(tmpYear);
		return this;
	}

	private String getDefaultName(Year year) {
		if (year != null) return year.getName() + "-" + year.getClasses().size() + 1;
		throw new SvedPrintException(SvedPrintExceptionType.MISSING_CLASS_NAME);
	}

}
