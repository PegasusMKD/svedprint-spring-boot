package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.models.Student;
import com.svedprint.main.models.SubjectOrientation;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class SubjectOrientationDtoDecorator extends SubjectOrientationDto {

    private static <T> List<T> ofNullableList(List<T> elements, List<T> entityValues, SubjectOrientation entity) {
        if (elements != null && elements.isEmpty()) {
            if (entity.getSubjects().isEmpty()) {
                return new ArrayList<>();
            } else {
                return entityValues;
            }
        }
        return elements;
    }

    private static void handleStudents(List<String> subjects, SubjectOrientation entity, boolean add) {

        List<String> entitySubjects = entity.getSubjects();
        System.out.println(entity.getId());
        if (add) {
            for (Student student : entity.getStudents()) {
                List<Integer> grades = student.getGrades();
                for (int i = 0; i < subjects.size() - entitySubjects.size(); i++) {
                    grades.add(0);
                }
                student.setGrades(grades);
            }
        } else {
            int idx = 0;
            int maxDifference = entitySubjects.size() - subjects.size();
            List<Student> students = entity.getStudents();
            List<Integer> grades;

            for (String subject : entitySubjects) {
                if (idx == maxDifference) {
                    break;
                }

                if (!subjects.contains(subject)) {
                    idx++;
                    int idxSubject = entitySubjects.indexOf(subject);
                    for (Student student : students) {
                        grades = student.getGrades();
                        grades.remove(idxSubject);
                        student.setGrades(grades);
                    }
                }
            }
        }
    }

    public SubjectOrientationDto init(SubjectOrientation entity, boolean update) {
        shortName = ofNullable(shortName).orElse(ofNullable(entity.getShortName()).orElse("Име"));
        fullName = ofNullable(fullName).orElse(ofNullable(fullName).orElse("Целосно име"));
        shortNames = ofNullable(shortNames).orElse(ofNullable(shortNames).orElse(new ArrayList<>()));
        subjects = ofNullableList(subjects, entity.getSubjects(), entity);
        if (subjects.size() != entity.getSubjects().size()) {
            handleStudents(subjects, entity, subjects.size() > entity.getSubjects().size());
        }
        return this;
    }

}
