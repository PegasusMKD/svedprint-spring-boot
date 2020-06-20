package com.svedprint.main.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TeacherDto {
    public String id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String username;
    public String password;
    public String token;
    public boolean printAllowed;
    public SchoolDto school;
    public SchoolClassDto schoolClass;
}
