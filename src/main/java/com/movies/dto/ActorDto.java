package com.movies.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(description = "Details about an actor")
public class ActorDto {
    private Long id;
    private String name;
    @Range(min = 1, max = 120)
    private Long age;
}
