package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Year {

	@JsonIgnore
	@Column(name = "year_id", length = 36)
	@GeneratedValue(generator = "strategy-uuid2")
	@GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
	@Id
	private String id;

	// TODO: Try to implement with LocalDate instead of Date
	@Column(name = "date_when_testimony_confirmed")
	@Temporal(TemporalType.DATE)
	private Date dateWhenTestimonyConfirmed; // Date of the meeting when the teachers confirmed the grades

	@Column(name = "year_name", length = 4)
	private String name; // TODO: Maybe make this (as well as previous years) enum

	@ManyToOne(fetch = FetchType.LAZY)
	private School school;

	@OneToMany(mappedBy = "year", fetch = FetchType.LAZY)
	private List<SchoolClass> classes = new ArrayList<>();

	@OneToMany(mappedBy = "year", fetch = FetchType.LAZY)
	private List<SubjectOrientation> subjectOrientations = new ArrayList<>();
}
