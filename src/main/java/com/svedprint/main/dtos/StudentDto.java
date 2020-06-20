package com.svedprint.main.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.svedprint.main.dtos.helperDtos.PromDataDto;
import com.svedprint.main.models.enums.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentDto {
    public String id;
    public Gender gender;
    public int cpyCounter;
    public String first_name;
    public String middle_name;
    public String last_name;
    public int number;
    public String fathersName;
    public String mothersName;
    public Date dateOfBirth;
    public String placeOfResidence;
    public String placeOfBirth;
    public String timesStudiedYear;
    public String timesTakenExam;
    public String examMonth;
    public PassedExam passedExam;
    public PassedYear passedYear;
    public int justifiedAbsences;
    public int unjustifiedAbsences;
    public ExamType examType;
    public EducationType educationType;
    public StudentType studentType;
    public BehaviorType behaviorType;
    public String lastGradeYear; // TODO: Maybe make this an enum aswell
    public SuccessType lastSchoolYearSuccessType;
    public Date lastSchoolYear;
    public String lastSchoolName;
    public String lastBusinessNumber; // Деловоден број
    public Date dateWhenTestimonyWasPrinted; // Testimony - Свидетелство
    public String citizenship;
    public String nameOfSchool; // Not sure if needed...? Since the name can be taken from the main object (might be a field that was added for some database fixing purposes)
    public boolean printedTestimony;
    public String optionalSubjects; // TODO: Maybe this should be reworked, not sure what the field is currently used for, maybe it could be a list of integers
    public int diplomaBusinessNumber;
    public List<String> languages; // TODO: Maybe this should be reworked so that it doesn't send languages as 0:1
    public Map<String, PromDataDto> maturska; // TODO: Check how it's stored in base currently, and then make a decision based on that (prom - матурска)
    public List<Integer> grades;
    public List<Integer> droppedGrades;
    public SchoolClassDto schoolClass;

    @JsonIgnore
    public boolean isIdSet() {
        return id != null;
    }
}
