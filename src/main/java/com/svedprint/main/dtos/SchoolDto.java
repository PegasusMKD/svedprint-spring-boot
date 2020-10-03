package com.svedprint.main.dtos;

import com.svedprint.main.dtos.helperDtos.Identifiable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SchoolDto extends Identifiable<String> {
	protected String name;
	protected String actNumber;
	protected Date actDate;
	protected String directorName;
	protected String businessNumber;
	protected String mainBook;
	protected String ministry;
	protected String country;
	protected String city;
	protected String lastDigitsOfYear;
	protected List<Date> printDatesForDiploma;
	protected List<Date> printDatesForTestimony;
	protected List<YearDto> years;
	protected List<TeacherDto> teachers;
}
