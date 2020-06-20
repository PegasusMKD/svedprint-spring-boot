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
public class SubjectOrientationDto {
    public String id;
    public String shortName;
    public String fullName;
    public List<String> subjects;
    public List<String> shortNames;
    public YearDto year;
    public Set<SchoolClassDto> classes;
}
