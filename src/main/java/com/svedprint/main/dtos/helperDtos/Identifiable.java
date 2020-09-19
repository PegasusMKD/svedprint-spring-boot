package com.svedprint.main.dtos.helperDtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Identifiable<T> {
	T id;

	@JsonIgnore
	public boolean isIdSet() {
		return id != null && !id.toString().isEmpty();
	}
}
