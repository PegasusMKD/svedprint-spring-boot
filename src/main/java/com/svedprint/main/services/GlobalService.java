package com.svedprint.main.services;

import com.svedprint.main.dtos.*;
import com.svedprint.main.services.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Deprecated
@Service
public class GlobalService {
	// TODO: Delete after SQL scripts get created for a base/test database

	private final TeacherDtoService teacherDtoService;
	private final SchoolDtoService schoolDtoService;
	private final SchoolClassDtoService schoolClassDtoService;
	private final StudentDtoService studentDtoService;
	private final SubjectOrientationDtoService subjectOrientationDtoService;
	private final YearDtoService yearDtoService;

	@Lazy
	public GlobalService(TeacherDtoService teacherDtoService, SchoolDtoService schoolDtoService,
						 SchoolClassDtoService schoolClassDtoService, StudentDtoService studentDtoService,
						 SubjectOrientationDtoService subjectOrientationDtoService, YearDtoService yearDtoService) {
		this.teacherDtoService = teacherDtoService;
		this.schoolDtoService = schoolDtoService;
		this.schoolClassDtoService = schoolClassDtoService;
		this.studentDtoService = studentDtoService;
		this.subjectOrientationDtoService = subjectOrientationDtoService;
		this.yearDtoService = yearDtoService;
	}


	public void initDB() throws IOException {
		List<String> yearNames = Arrays.asList("I", "II", "III");

		SchoolDto schoolDto = new SchoolDto();
		schoolDto.setName("Korcagin");
		schoolDto.setDirectorName("Filip Jovanov");
		System.out.println(schoolDto);
		SchoolDto school = schoolDtoService.save(schoolDto);


		List<YearDto> years = new ArrayList<>();
		for (String yearName : yearNames) {
			YearDto yearDto = new YearDto();
			yearDto.setName(yearName);
			yearDto.setSchool(school);
			years.add(yearDtoService.save(yearDto));
		}

		List<SubjectOrientationDto> mandatory = createMandatoryBase();

		HashMap<YearDto, List<SubjectOrientationDto>> subjectOrientationsMap = new HashMap<>();
		for (YearDto yearDto : years) {
			List<SubjectOrientationDto> subjectOrientations = new ArrayList<>();
			for (SubjectOrientationDto subjectOrientationDto : mandatory) {
				subjectOrientationDto.setYear(yearDto);
				subjectOrientationDto.setClasses(null);
				subjectOrientations.add(subjectOrientationDtoService.oldSave(subjectOrientationDto));
			}
			subjectOrientationsMap.put(yearDto, subjectOrientations);
		}


		List<TeacherDto> teachers = new ArrayList<>();
		List<SchoolClassDto> classes = new ArrayList<>();
		for (YearDto yearDto : subjectOrientationsMap.keySet()) {
			for (int i = 1; i <= 2; i++) {
				SchoolClassDto schoolClassDto = new SchoolClassDto();
				schoolClassDto.setName(yearDto.getName() + "-" + i);
				schoolClassDto.setYear(yearDto);
				schoolClassDto.setSubjectOrientations(subjectOrientationsMap.get(yearDto));
				schoolClassDto = schoolClassDtoService.save(schoolClassDto);
				classes.add(schoolClassDto);
				TeacherDto teacherDto = new TeacherDto();
				teacherDto.setSchoolClass(schoolClassDto);
				teacherDto.setSchool(school);
				teacherDto.setFirstName("Filip");
				teacherDto.setLastName("Jovanov");
				teacherDto.setUsername(RandomStringUtils.randomAlphanumeric(10));
				teacherDto.setPassword(RandomStringUtils.randomAlphanumeric(10));
				teacherDtoService.save(teacherDto);
				teachers.add(teacherDto);
			}
		}

		for (SchoolClassDto classDto : classes) {
			for (int i = 0; i < 10; i++) {
				StudentDto student = new StudentDto();
				student.setSchoolClass(classDto);
				ArrayList<SubjectOrientationDto> subjectOrientationsArray = new ArrayList<>(classDto.getSubjectOrientations());
				student.setSubjectOrientation(subjectOrientationsArray.get(RandomUtils.nextInt(0, subjectOrientationsArray.size())));
				studentDtoService.oldSave(student, false);
			}
		}

		FileWriter fileWriter = new FileWriter("./teachers-data.txt");
		for (TeacherDto teacherDto : teachers) {
			fileWriter.write(String.format("Class:%s\n\tUsername: %s\n\tPassword: %s\n", teacherDto.getSchoolClass().getName(), teacherDto.getUsername(), teacherDto.getPassword()));
		}
		fileWriter.close();
		System.out.println("finished initDB");
	}


	private List<SubjectOrientationDto> createMandatoryBase() {
		List<SubjectOrientationDto> subjectOrientationDtos = new ArrayList<>();

		SubjectOrientationDto optionalSubjects = new SubjectOrientationDto();
		optionalSubjects.setFullName("цел смер");
		optionalSubjects.setSubjects(new ArrayList<>());
		optionalSubjects.setShortName("Изборни Предмети");
		subjectOrientationDtos.add(optionalSubjects);

		SubjectOrientationDto languages = new SubjectOrientationDto();
		languages.setFullName("цел смер");
		languages.setSubjects(new ArrayList<>());
		languages.setShortName("Странски Јазици");
		subjectOrientationDtos.add(languages);

		SubjectOrientationDto projectSubjects = new SubjectOrientationDto();
		projectSubjects.setFullName("Проектни Активности");
		projectSubjects.setSubjects(new ArrayList<>());
		projectSubjects.setShortName("ПА");
		subjectOrientationDtos.add(projectSubjects);

		SubjectOrientationDto extraDemo = new SubjectOrientationDto();
		extraDemo.setShortName("Demo");
		extraDemo.setSubjects(new ArrayList<>());
		extraDemo.setFullName("Demo Orientation");
		subjectOrientationDtos.add(extraDemo);

		return subjectOrientationDtos;
	}

}
