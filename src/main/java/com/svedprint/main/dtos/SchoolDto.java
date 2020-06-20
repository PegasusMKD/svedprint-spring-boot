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
public class SchoolDto {
    public String id;
    public String name;
    public String actNumber;
    public Date actDate;
    public String directorName;
    public String businessNumber;
    public String mainBook;
    public String ministry;
    public String country;
    public String city;
    public String lastDigitsOfYear;
    public List<Date> printDatesForDiploma;
    public List<Date> printDatesForTestimony;
    public List<YearDto> years;
    public List<TeacherDto> teachers;
}
