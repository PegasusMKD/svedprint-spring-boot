package com.svedprint.main.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SchoolClassDto {
    public String id;
    public String name;
    public YearDto year;
    public List<StudentDto> students;
    public Set<SubjectOrientationDto> subjectOrientations;
    public TeacherDto teacher;
}
