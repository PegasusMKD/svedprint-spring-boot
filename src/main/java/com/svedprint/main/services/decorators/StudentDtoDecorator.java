package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.StudentDto;
import com.svedprint.main.models.Student;
import com.svedprint.main.models.enums.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class StudentDtoDecorator extends StudentDto {

    // TODO: Add needed repository and mappers to be able to write the other 2 functions below
    public StudentDto init(Student entity, boolean update) {

        gender = ofNullable(gender).orElse(ofNullable(entity.getGender()).orElse(Gender.MALE));
        cpyCounter = ofNullable(cpyCounter).orElse(ofNullable(entity.getCpyCounter()).orElse(getCpyCtr()));
        firstName = ofNullable(firstName).orElse(ofNullable(entity.getFirstName()).orElse("Име"));
        middleName = ofNullable(middleName).orElse(ofNullable(entity.getMiddleName()).orElse("Средно Име"));
        lastName = ofNullable(lastName).orElse(ofNullable(entity.getLastName()).orElse("Презиме"));
        number = ofNullable(number).orElse(ofNullable(entity.getNumber()).orElse(getDecoratorNumber()));
        fathersName = ofNullable(fathersName).orElse(ofNullable(entity.getFathersName()).orElse("Татково име"));
        mothersName = ofNullable(mothersName).orElse(ofNullable(entity.getMothersName()).orElse("Мајкино име"));
        dateOfBirth = ofNullable(dateOfBirth).orElse(ofNullable(entity.getDateOfBirth()).orElse(new Date()));
        placeOfResidence = ofNullable(placeOfResidence).orElse(ofNullable(entity.getPlaceOfResidence()).orElse("Место на живеење"));
        placeOfBirth = ofNullable(placeOfBirth).orElse(ofNullable(entity.getPlaceOfBirth()).orElse("Место на раѓање"));
        timesStudiedYear = ofNullable(timesStudiedYear).orElse(ofNullable(entity.getTimesStudiedYear()).orElse("прв"));
        timesTakenExam = ofNullable(timesTakenExam).orElse(ofNullable(entity.getTimesTakenExam()).orElse("прв"));
        examMonth = ofNullable(examMonth).orElse(ofNullable(entity.getExamMonth()).orElse("Јуни"));
        passedExam = ofNullable(passedExam).orElse(ofNullable(entity.getPassedExam()).orElse(PassedExam.PASSED));
        passedYear = ofNullable(passedYear).orElse(ofNullable(entity.getPassedYear()).orElse(PassedYear.PASSED_YEAR));
        justifiedAbsences = ofNullable(justifiedAbsences).orElse(ofNullable(entity.getJustifiedAbsences()).orElse(0));
        unjustifiedAbsences = ofNullable(unjustifiedAbsences).orElse(ofNullable(entity.getUnjustifiedAbsences()).orElse(0));
        examType = ofNullable(examType).orElse(ofNullable(entity.getExamType()).orElse(ExamType.STATE));
        educationType = ofNullable(educationType).orElse(ofNullable(entity.getEducationType()).orElse(EducationType.GYMNASIUM));
        studentType = ofNullable(studentType).orElse(ofNullable(entity.getStudentType()).orElse(StudentType.REGULAR));
        behaviorType = ofNullable(behaviorType).orElse(ofNullable(entity.getBehaviorType()).orElse(BehaviorType.EXEMPLARY));
        lastGradeYear = ofNullable(lastGradeYear).orElse(ofNullable(entity.getLastGradeYear()).orElse("I"));
        lastSchoolYearSuccessType = ofNullable(lastSchoolYearSuccessType).orElse(ofNullable(entity.getLastSchoolYearSuccessType()).orElse(SuccessType.EXCELLENT));
        lastSchoolYear = ofNullable(lastSchoolYear).orElse(ofNullable(entity.getLastSchoolYear()).orElse((Calendar.getInstance().get(Calendar.YEAR) % 100) - 1));
        lastSchoolName = ofNullable(lastSchoolName).orElse(ofNullable(entity.getLastSchoolName()).orElse(getDecoratorLastSchoolName()));
        lastBusinessNumber = ofNullable(lastBusinessNumber).orElse(ofNullable(entity.getLastBusinessNumber()).orElse("12345/5"));
        dateWhenTestimonyWasPrinted = ofNullable(dateWhenTestimonyWasPrinted).orElse(ofNullable(entity.getDateWhenTestimonyWasPrinted()).orElse(new GregorianCalendar(2004, Calendar.FEBRUARY, 11).getTime()));
        citizenship = ofNullable(citizenship).orElse(ofNullable(entity.getCitizenship()).orElse("македонско/граќанин на Република Северна Македонија"));
        nameOfSchool = null;
        optionalSubjects = ofNullable(optionalSubjects).orElse(ofNullable(entity.getOptionalSubjects()).orElse(""));
        diplomaBusinessNumber = ofNullable(diplomaBusinessNumber).orElse(ofNullable(entity.getDiplomaBusinessNumber()).orElse(0));
        languages = ofNullable(languages).orElse(ofNullable(entity.getLanguages()).orElse(new ArrayList<>()));

        // Should add mapper for the maturska
//        maturska = ofNullable(maturska).orElse(ofNullable(entity.getMaturska()).orElse(null));
        maturska = null;
        ArrayList<Integer> tmpGrades = new ArrayList<>();
        ArrayList<Integer> tmpDroppedGrades = new ArrayList<>();
        for (int i = 0; i < entity.getSubjectOrientation().getSubjects().size(); i++) {
            tmpGrades.add(0);
        }
        for (int i = 0; i < entity.getSubjectOrientation().getSubjects().size(); i++) {
            tmpDroppedGrades.add(0);
        }
        grades = ofNullable(grades).orElse(ofNullable(entity.getGrades()).orElse(tmpGrades));
        droppedGrades = ofNullable(droppedGrades).orElse(ofNullable(entity.getDroppedGrades()).orElse(tmpDroppedGrades));

        return this;
    }

    private String getDecoratorLastSchoolName() {
        return null;
    }

    private Integer getDecoratorNumber() {
        return null;
    }

    private int getCpyCtr() {
        // TODO: Implement if needed
        return 0;
    }
}
