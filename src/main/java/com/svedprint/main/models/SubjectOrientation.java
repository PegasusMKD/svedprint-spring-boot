package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectOrientation {

    // TODO: Rework code so that it makes a copy of an existing SubjectOrientation, instead of just setting a relationship to the existing one

    @JsonIgnore
    @Column(name = "subject_orientation_id", length = 36)
    @GeneratedValue(generator = "strategy-uuid2")
    @GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
    @Id
    private String id;

    @Column(name = "short_name_of_orientation")
    private String shortName;

    @Column(name = "full_name_of_orientation")
    private String fullName;

    @ElementCollection
    private List<String> subjects;

    @ElementCollection
    private List<String> shortNames;

    @ManyToOne(fetch = FetchType.LAZY)
    private Year year;

    @ManyToMany
    private Set<SchoolClass>classes;
}
