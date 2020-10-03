package com.svedprint.main.dtos;

import com.svedprint.main.dtos.helperDtos.Identifiable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SubjectOrientationDto extends Identifiable<String> {
	protected String shortName;
	protected String fullName;
	protected List<String> subjects;
	protected List<String> shortNames;
	protected YearDto year;
	protected SchoolClassDto classes;
	protected List<StudentDto> students;
}
