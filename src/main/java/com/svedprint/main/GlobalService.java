package com.svedprint.main;

import com.svedprint.main.dtos.*;
import com.svedprint.main.services.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GlobalService {


    @Autowired
    private TeacherDtoService teacherDtoService;

    @Autowired
    private SchoolDtoService schoolDtoService;

    @Autowired
    private SchoolClassDtoService schoolClassDtoService;

    @Autowired
    private StudentDtoService studentDtoService;

    @Autowired
    private SubjectOrientationDtoService subjectOrientationDtoService;

    @Autowired
    private YearDtoService yearDtoService;


    public void initDB() throws IOException {
        List<String> yearNames = new ArrayList<String>() {{
            add("I");
            add("II");
            add("III");
        }};

        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("Korcagin");
        schoolDto.setDirectorName("Filip Jovanov");
        System.out.println(schoolDto);
        SchoolDto school = schoolDtoService.save(schoolDto, false);


        List<YearDto> years = new ArrayList<>();
        for (String yearName : yearNames) {
            YearDto yearDto = new YearDto();
            yearDto.setName(yearName);
            yearDto.setSchool(school);
            years.add(yearDtoService.save(yearDto, false));
        }

        List<SubjectOrientationDto> mandatory = createMandatoryBase();

        HashMap<YearDto, List<SubjectOrientationDto>> subjectOrientationsMap = new HashMap<>();
        for (YearDto yearDto : years) {
            List<SubjectOrientationDto> subjectOrientations = new ArrayList<>();
            for (SubjectOrientationDto subjectOrientationDto : mandatory) {
                subjectOrientationDto.setYear(yearDto);
                subjectOrientationDto.setClasses(null);
                subjectOrientations.add(subjectOrientationDtoService.oldSave(subjectOrientationDto, false));
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
                schoolClassDto = schoolClassDtoService.save(schoolClassDto, false);
                classes.add(schoolClassDto);
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setSchoolClass(schoolClassDto);
                teacherDto.setSchool(school);
                teacherDto.setFirstName("Filip");
                teacherDto.setLastName("Jovanov");
                teacherDto.setUsername(RandomStringUtils.randomAlphanumeric(10));
                teacherDto.setPassword(RandomStringUtils.randomAlphanumeric(10));
                teacherDtoService.save(teacherDto, false);
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
            fileWriter.write(String.format("Class:%s\n\tUsername:%s\n\tPassword:%s\n", teacherDto.getSchoolClass().getName(), teacherDto.getUsername(), teacherDto.getPassword()));
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
