package com.svedprint.main.services.decorators;

import com.svedprint.main.dtos.TeacherDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.models.Teacher;
import de.mkammerer.argon2.Argon2;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static java.util.Optional.ofNullable;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
public class TeacherDtoDecorator extends TeacherDto {

    public TeacherDto init(Teacher entity, boolean update, Argon2 argon2) {
        firstName = ofNullable(firstName).orElse(ofNullable(entity.getFirstName()).orElse("Име"));
        middleName = ofNullable(middleName).orElse(ofNullable(entity.getMiddleName()).orElse(""));
        lastName = ofNullable(lastName).orElse(ofNullable(entity.getLastName()).orElse("Презиме"));

        if (password != null && !password.isEmpty()) {
            password = argon2.hash(4, 5000, 2, password);
        }

        if (update) {
            username = ofNullable(username).orElse(ofNullable(entity.getUsername()).orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.NO_USERNAME_PROVIDED)));
        } else {
            username = ofNullable(username).orElseThrow(() -> new SvedPrintException(SvedPrintExceptionType.NO_USERNAME_PROVIDED));
        }

        return this;
    }

}
