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
@EqualsAndHashCode
public class PromDataDto {
    // TODO: Add data that prom subjects contain
    public String id;
}
