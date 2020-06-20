package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.svedprint.main.models.enums.*;
import com.svedprint.main.models.helperClasses.PromData;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Student {

    // TODO: Add relations to SubjectDirection/Orientation/SubjectOrientation

    @JsonIgnore
    @Column(name = "student_id", length = 36)
    @GeneratedValue(generator = "strategy-uuid2")
    @GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
    @Id
    private String id;

    @Column(name = "gender", length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "cpy_counter")
    private Integer cpyCounter;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "number")
    private Integer number;

    @Column(name="fathers_name", length = 100)
    private String fathersName;

    @Column(name="mothers_name", length = 100)
    private String mothersName;

    @Column(name="date_of_birth", length = 10)
    private Date dateOfBirth;

    @Column(name="place_of_residence", length = 200)
    private String placeOfResidence;

    @Column(name="place_of_birth", length = 200)
    private String placeOfBirth;

    @Column(name="times_studied_year", length = 10)
    private String timesStudiedYear;

    @Column(name="times_taken_exam", length = 10)
    private String timesTakenExam;

    @Column(name="exam_month", length = 20)
    private String examMonth;

    @Column(name="passed_exam", length = 20)
    @Enumerated(EnumType.STRING)
    private PassedExam passedExam;

    @Column(name="passed_year", length = 20)
    @Enumerated(EnumType.STRING)
    private PassedYear passedYear;

    @Column(name = "justified_absences")
    private Integer justifiedAbsences;

    @Column(name = "unjustified_absences")
    private Integer unjustifiedAbsences;

    @Column(name="exam_types", length = 20)
    @Enumerated(EnumType.STRING)
    private ExamType examType;

    @Column(name="education_type", length = 20)
    @Enumerated(EnumType.STRING)
    private EducationType educationType;


    @Column(name="student_type", length = 20)
    @Enumerated(EnumType.STRING)
    private StudentType studentType;

    @Column(name="behavior", length = 20)
    @Enumerated(EnumType.STRING)
    private BehaviorType behaviorType;

    // TODO: Check if field "nahnaden" from old model is used

    @Column(name="last_grade_year")
    private String lastGradeYear; // TODO: Maybe make this an enum aswell

    @Column(name="last_school_year_success", length = 15)
    @Enumerated(EnumType.STRING)
    private SuccessType lastSchoolYearSuccessType;

    @Column(name = "last_school_year")
    private Integer lastSchoolYear;

    @Column(name="last_school_name", length = 200)
    private String lastSchoolName;

    @Column(name="last_business_number", length = 50)
    private String lastBusinessNumber; // Деловоден број

    @Column(name = "date_when_testimony_was_printed")
    private Date dateWhenTestimonyWasPrinted; // Testimony - Свидетелство

    @Column(name="citizenship")
    private String citizenship;

    @Column(name="name_of_school")
    private String nameOfSchool; // Not sure if needed...? Since the name can be taken from the main object (might be a field that was added for some database fixing purposes)

    @Column(name="printed_testimony", length = 1)
    private boolean printedTestimony;

    @Column(name="optional_subjects")
    private String optionalSubjects; // TODO: Maybe this should be reworked, not sure what the field is currently used for, maybe it could be a list of integers

    @Column(name = "diploma_business_number")
    private Integer diplomaBusinessNumber;

    @ElementCollection
    private List<String> languages; // TODO: Maybe this should be reworked so that it doesn't send languages as 0:1

    @ManyToMany(cascade = CascadeType.ALL)
    private Map<String, PromData> maturska; // TODO: Check how it's stored in base currently, and then make a decision based on that (prom - матурска)

    @ElementCollection
    private List<Integer> grades;

    @ElementCollection
    private List<Integer> droppedGrades;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubjectOrientation subjectOrientation;
}
