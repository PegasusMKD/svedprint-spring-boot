package com.svedprint.main.dtos;

import com.svedprint.main.dtos.helperDtos.Identifiable;
import com.svedprint.main.dtos.helperDtos.PromDataDto;
import com.svedprint.main.models.enums.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends Identifiable<String> {
	protected Gender gender;
	protected Integer cpyCounter;
	protected String firstName;
	protected String middleName;
	protected String lastName;
	protected Integer number;
	protected String fathersName;
	protected String mothersName;
	protected Date dateOfBirth;
	protected String placeOfResidence;
	protected String placeOfBirth;
	protected String timesStudiedYear;
	protected String timesTakenExam;
	protected String examMonth;
	protected PassedExam passedExam;
	protected PassedYear passedYear;
	protected Integer justifiedAbsences;
	protected Integer unjustifiedAbsences;
	protected ExamType examType;
	protected EducationType educationType;
	protected StudentType studentType;
	protected BehaviorType behaviorType;
	protected String lastGradeYear; // TODO: Maybe make this an enum aswell
	protected SuccessType lastSchoolYearSuccessType;
	protected Integer lastSchoolYear;
	protected String lastSchoolName;
	protected String lastBusinessNumber; // Деловоден број
	protected Date dateWhenTestimonyWasPrinted; // Testimony - Свидетелство
	protected String citizenship;
	protected String nameOfSchool; // Not sure if needed...? Since the name can be taken from the main object (might be a field that was added for some database fixing purposes)
	protected boolean printedTestimony;
	protected String optionalSubjects; // TODO: Maybe this should be reworked, not sure what the field is currently used for, maybe it could be a list of integers
	protected Integer diplomaBusinessNumber;
	protected List<String> languages; // TODO: Maybe this should be reworked so that it doesn't send languages as 0:1
	protected Map<String, PromDataDto> maturska; // TODO: Check how it's stored in base currently, and then make a decision based on that (prom - матурска)
	protected List<Integer> grades;
	protected List<Integer> droppedGrades;
	protected SchoolClassDto schoolClass;
	protected SubjectOrientationDto subjectOrientation;
}
