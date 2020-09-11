package com.movies.controllers;

import com.movies.dto.ActorDto;
import com.movies.services.ActorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("actors")
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @GetMapping
    @ApiOperation(value = "Retrieve all actors",
            response = ActorDto.class,
            responseContainer = "List")
    public List<ActorDto> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/movies/{id}")
    @ApiOperation(value = "Retrieve all actors from a specified movie",
            response = ActorDto.class,
            responseContainer = "List")
    public List<ActorDto> getAllActorsFromMovie(@ApiParam(value = "id of the movie", required = true)
                                                @PathVariable @Valid @Min(0) Long id) {
        return actorService.getActorsFromMovie(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new actor")
    @ResponseStatus(HttpStatus.CREATED)
    public String addActor(@RequestBody @Valid ActorDto actorDto) {
        actorService.addActor(actorDto);
        return "Actor added";
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing actor name")
    @ResponseStatus(HttpStatus.OK)
    public String updateActorName(@ApiParam(value = "id of the actor to update", required = true)
                                  @PathVariable @Valid @Min(0) Long id, @RequestParam String name) {
        actorService.updateActorName(id, name);
        return "Actor name updated";
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an actor")
    @ResponseStatus(HttpStatus.OK)
    public String deleteActor(@ApiParam(value = "id of the actor to delete", required = true)
                              @PathVariable @Valid @Min(0) Long id) {
        actorService.deleteActor(id);
        return "Actor deleted";
    }
}
