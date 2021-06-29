package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SchoolDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.models.School;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
public class SchoolDtoDecorator extends SchoolDto {

	public SchoolDto init(School entity) {

		actNumber = ofNullable(actNumber).orElse(ofNullable(entity.getActNumber()).orElse("12345/1"));
		actDate = ofNullable(actDate).orElse(ofNullable(entity.getActDate()).orElse(new GregorianCalendar(2004, Calendar.FEBRUARY, 11).getTime()));
		businessNumber = ofNullable(businessNumber).orElse(ofNullable(entity.getBusinessNumber()).orElse("128/4"));
		mainBook = ofNullable(mainBook).orElse(ofNullable(entity.getMainBook()).orElse("128/4"));
		ministry = ofNullable(ministry).orElse(ofNullable(entity.getMinistry()).orElse("Министерство за образование и наука"));
		country = ofNullable(country).orElse(ofNullable(entity.getCountry()).orElse("Република Северна Македонија"));
		city = ofNullable(city).orElse(ofNullable(entity.getCity()).orElse("Скопје"));
		lastDigitsOfYear = ofNullable(lastDigitsOfYear).orElse(ofNullable(entity.getLastDigitsOfYear()).orElse(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) % 100)));
		printDatesForDiploma = ofNullable(printDatesForDiploma).orElse(ofNullable(entity.getPrintDatesForDiploma()).orElse(new ArrayList<>()));
		printDatesForTestimony = ofNullable(printDatesForTestimony).orElse(ofNullable(entity.getPrintDatesForTestimony()).orElse(new ArrayList<>()));
		name = ofNullable(name).orElseGet(() -> ofNullable(entity.getName()).orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.NO_SCHOOL_ASSIGNED)));
		directorName = ofNullable(directorName).orElseGet(() -> ofNullable(entity.getDirectorName()).orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.DIRECTOR_NAME_MISSING)));
		return this;
	}
}
