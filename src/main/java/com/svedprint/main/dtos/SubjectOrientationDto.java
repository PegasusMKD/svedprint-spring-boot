package com.svedprint.main.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SubjectOrientationDto {
    public String id;
    public String shortName;
    public String fullName;
    public List<String> subjects;
    public List<String> shortNames;
    public YearDto year;
    public SchoolClassDto classes;
    public List<StudentDto> students;

    @JsonIgnore
    public boolean isIdSet() {
        return id != null;
    }
}
