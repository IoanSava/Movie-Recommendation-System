package com.movies.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@ApiModel(description = "Details about an actor")
public class ActorDto {
    private Long id;
    private String name;

    @Range(min = 1, max = 120)
    private Long age;
}
