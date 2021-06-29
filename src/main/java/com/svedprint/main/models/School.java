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

	// TODO: Try to implement with LocalDate instead of Date
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

	// TODO: Try to implement with LocalDate instead of Date
	@ElementCollection
	@Temporal(TemporalType.DATE)
	private List<Date> printDatesForDiploma;

	// TODO: Try to implement with LocalDate instead of Date
	@ElementCollection
	@Temporal(TemporalType.DATE)
	private List<Date> printDatesForTestimony;


	// TODO: Disable cascade (or check why cascade was even used in the first place)
	@OneToMany(mappedBy = "school")
	private List<Year> years = new ArrayList<>();

	@OneToMany(mappedBy = "school")
	private List<Teacher> teachers = new ArrayList<>();
}
