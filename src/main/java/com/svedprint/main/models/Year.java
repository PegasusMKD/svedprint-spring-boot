package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Year {

    @JsonIgnore
    @Column(name = "year_id", length = 36)
    @GeneratedValue(generator = "strategy-uuid2")
    @GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
    @Id
    private String id;

    @Column(name = "date_when_testimony_confirmed")
    private Date dateWhenTestimonyConfirmed;

    @Column(name = "year_name", length = 4)
    private String name; // TODO: Maybe make this (as well as previous years) enum

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL)
    private List<SchoolClass> classes;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL)
    private List<SubjectOrientation> subjectOrientations;
}
