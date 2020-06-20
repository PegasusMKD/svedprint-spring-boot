package com.svedprint.main.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class YearDto {
    public String id;
    public Date dateWhenTestimonyConfirmed;
    public String name; // TODO: Maybe make this (as well as previous years) enum
    public SchoolDto school;
    public List<SchoolClassDto> classes;
    public List<SubjectOrientationDto> subjectOrientations;
}
