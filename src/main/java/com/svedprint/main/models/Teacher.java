package com.svedprint.main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Teacher {

	// TODO: Refactor tokenizing and all that to OAuth 2.0 with JWT

	@JsonIgnore
	@Column(name = "teacher_id", length = 36)
	@GeneratedValue(generator = "strategy-uuid2")
	@GenericGenerator(name = "strategy-uuid2", strategy = "uuid2")
	@Id
	private String id;

	@Column(name = "first_name", length = 45)
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_class_id")
	private SchoolClass schoolClass;
}
