package com.svedprint.main;

import com.svedprint.main.dtos.*;
import com.svedprint.main.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GlobalService {

    @Autowired
    private static TeacherDtoService teacherDtoService;

    @Autowired
    private static SchoolDtoService schoolDtoService;

    @Autowired
    private static SchoolClassDtoService schoolClassDtoService;

    @Autowired
    private static StudentDtoService studentDtoService;

    @Autowired
    private static SubjectOrientationDtoService subjectOrientationDtoService;

    @Autowired
    private static YearDtoService yearDtoService;


    public static void initDB() {
        List<String> yearNames = new ArrayList<String>() {{
            add("I");
            add("II");
            add("III");
        }};


        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("Korcagin");
        schoolDto.setDirectorName("Filip Jovanov");
        SchoolDto school = schoolDtoService.save(schoolDto, false);


        List<YearDto> years = new ArrayList<>();
        for (String yearName : yearNames) {
            YearDto yearDto = new YearDto();
            yearDto.setName(yearName);
            yearDto.setSchool(school);
            years.add(yearDtoService.save(yearDto, false));
        }

        HashMap<YearDto, List<SubjectOrientationDto>> subjectOrientationsMap = new HashMap<>();
        for (YearDto yearDto : years) {
            List<SubjectOrientationDto> subjectOrientations = new ArrayList<>();
//            for() TODO: Add the 3 mandatory subjectOrientation DTOs
            subjectOrientationsMap.put(yearDto, subjectOrientations);
        }


        List<TeacherDto> teachers = new ArrayList<>();
        List<SchoolClassDto> classes = new ArrayList<>();
        for (YearDto yearDto : years) {
            for (int i = 1; i <= 2; i++) {
                SchoolClassDto schoolClassDto = new SchoolClassDto();
                schoolClassDto.setName(yearDto.getName() + "-" + i);
                // TODO: Continue from here


            }
        }

    }

}
