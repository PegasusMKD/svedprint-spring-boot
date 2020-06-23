package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SchoolClass classes;

    @OneToMany(mappedBy = "subjectOrientation", cascade = CascadeType.ALL)
    private List<Student> students;
}
