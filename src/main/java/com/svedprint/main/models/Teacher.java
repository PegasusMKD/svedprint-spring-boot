package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Teacher {

    // TODO: Refactor tokenizing and all that to OAuth 2.0 with JWT

    @JsonIgnore
    @Column(name = "teacher_id", length = 36)
    @GeneratedValue(generator = "strategy-uuid2")
    @GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
    @Id
    private String id;

    @Column(name="first_name", length = 45)
    private String firstName;

    @Column(name = "middle_name", length = 45)
    private String middleName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "username", length = 60, nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "token", length = 20)
    private String token;

    @Column(name = "print_allowed")
    private boolean printAllowed;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    // TODO: Rework relation from OneToOne to a ManyToOne or something like that, so that professors can still exist
    @OneToOne(mappedBy = "teacher")
    private SchoolClass schoolClass;
}
