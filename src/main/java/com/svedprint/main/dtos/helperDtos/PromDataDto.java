package com.svedprint.main.dtos.helperDtos;

import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PromDataDto extends Identifiable<String> {
    // TODO: Add data that prom subjects contain
}
