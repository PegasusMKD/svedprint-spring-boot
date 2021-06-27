package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.models.Student;
import com.svedprint.main.models.SubjectOrientation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class SubjectOrientationDtoDecorator extends SubjectOrientationDto {

	private static <T> List<T> ofNullableList(List<T> elements, List<T> entityValues) {
		if (elements != null && elements.isEmpty()) {
			return entityValues;
		}
		return elements;
	}

	private static void handleStudents(List<String> subjects, SubjectOrientation entity, boolean add) {

		List<String> entitySubjects = entity.getSubjects();
		List<Student> students = entity.getStudents();
		int differentSubjects = Math.abs(entitySubjects.size() - subjects.size());

		if (add) addSubjectsToStudents(students, differentSubjects);
		else removeSubjectsFromStudents(subjects, entitySubjects, students, differentSubjects);
	}

	private static void removeSubjectsFromStudents(List<String> subjects, List<String> entitySubjects, List<Student> students, int differentSubjects) {
		for (String subject : entitySubjects) {
			if (differentSubjects == 0) {
				break;
			}

			if (!subjects.contains(subject)) {
				differentSubjects--;
				int idx = entitySubjects.indexOf(subject);
				students.forEach(student -> student.getGrades().remove(idx));
			}
		}
	}

	private static void addSubjectsToStudents(List<Student> students, int differentSubjects) {
		students.forEach(student -> {
			List<Integer> grades = student.getGrades();
			IntStream.range(0, differentSubjects).mapToObj(i -> 0).forEachOrdered(grades::add);
			student.setGrades(grades);
		});
	}

	public SubjectOrientationDto init(SubjectOrientation entity) {
		shortName = ofNullable(shortName).orElse(ofNullable(entity.getShortName()).orElse("Име"));
		fullName = ofNullable(fullName).orElse(ofNullable(fullName).orElse("Целосно име"));
		shortNames = ofNullable(shortNames).orElse(ofNullable(shortNames).orElse(new ArrayList<>()));
		subjects = ofNullableList(subjects, entity.getSubjects());
		if (subjects != null && subjects.size() != entity.getSubjects().size()) {
			handleStudents(subjects, entity, subjects.size() > entity.getSubjects().size());
		}
		return this;
	}

}
