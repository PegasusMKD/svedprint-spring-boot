package com.svedprint.main.dtos;

import com.svedprint.main.dtos.helperDtos.Identifiable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class YearDto extends Identifiable<String> {
	protected Date dateWhenTestimonyConfirmed;
	protected String name; // TODO: Maybe make this (as well as previous years) enum
	protected SchoolDto school;
	protected List<SchoolClassDto> classes = new ArrayList<>();
	protected List<SubjectOrientationDto> subjectOrientations = new ArrayList<>();
}
