package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
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
public class School {

    @JsonIgnore
    @Column(name = "school_id", length = 36)
    @GeneratedValue(generator = "strategy-uuid2")
    @GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
    @Id
    private String id;

    @Column(name = "name_of_school", length = 150)
    private String name;

    @Column(name = "act_number", length = 50)
    private String actNumber; // TODO: Refactor this into a List for GYMNASIUM and PROFESSIONAL

    @Column(name = "act_date")
    @Temporal(TemporalType.DATE)
    private Date actDate; // TODO: Refactor this into a list for GYMNASIUM and PROFESSIONAL

    @Column(name = "director_name", length = 60)
    private String directorName;

    @Column(name = "business_number", length = 50)
    private String businessNumber; // TODO: Might need refactoring since all of the differences in the number

    @Column(name = "main_book", length = 5)
    private String mainBook; // TODO: Maybe refactor

    @Column(name = "ministry", length = 200)
    private String ministry;

    @Column(name = "country", length = 150)
    private String country;

    @Column(name = "city", length = 150)
    private String city;

    @Column(name = "last_digits_of_year", length = 10)
    private String lastDigitsOfYear;

    @ElementCollection
    @Temporal(TemporalType.DATE)
    private List<Date> printDatesForDiploma;

    @ElementCollection
    @Temporal(TemporalType.DATE)
    private List<Date> printDatesForTestimony;


    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Year> years;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Teacher> teachers;
}
