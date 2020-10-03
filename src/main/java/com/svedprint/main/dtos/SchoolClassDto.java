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
public class SchoolClassDto extends Identifiable<String> {
	protected String name;
	protected YearDto year;
	protected List<StudentDto> students;
	protected List<SubjectOrientationDto> subjectOrientations;
	protected TeacherDto teacher;
}
