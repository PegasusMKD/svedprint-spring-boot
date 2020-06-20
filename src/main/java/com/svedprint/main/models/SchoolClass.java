package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SchoolClass {

    @JsonIgnore
    @Column(name = "school_class_id", length = 36)
    @GeneratedValue(generator = "strategy-uuid2")
    @GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
    @Id
    private String id;

    @Column(name = "name_of_class", length = 10)
    private String name; // TODO: Maybe remake this so that it's a ENUM + "-" + int/char

    @ManyToOne(fetch = FetchType.LAZY)
    private Year year;

    // TODO: Check if "otpishani", "otstraneti", "napushtile", "zapishale" is needed

    //TODO: Add Relation to SubjectOrientation
    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Student> students;

    @ManyToMany
    private Set<SubjectOrientation> subjectOrientations;

    @OneToOne
    private Teacher teacher;

}
