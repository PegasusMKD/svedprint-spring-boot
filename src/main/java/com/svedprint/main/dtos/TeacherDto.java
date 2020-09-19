package com.svedprint.main.dtos;

import com.svedprint.main.dtos.helperDtos.Identifiable;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends Identifiable<String> {
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String username;
    protected String password;
    protected String token;
    protected boolean printAllowed;
    protected SchoolDto school;
    protected SchoolClassDto schoolClass;
}
