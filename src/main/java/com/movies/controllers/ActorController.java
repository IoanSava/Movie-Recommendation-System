package com.movies.controllers;

import com.movies.dto.ActorDto;
import com.movies.services.ActorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/actors/")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all actors",
            response = ActorDto.class,
            responseContainer = "Set")
    public Set<ActorDto> getAllActors() {
        return actorService.getAllActors();
    }

    @PostMapping
    @ApiOperation(value = "Add a new actor")
    public ResponseEntity<String> addActor(@RequestBody @Valid ActorDto actorDto) {
        actorService.addActor(actorDto);
        return new ResponseEntity<>("Actor added", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update an existing actor name")
    public ResponseEntity<String> updateActorName(@ApiParam(value = "id of the actor you want to update", required = true)
                                                  @PathVariable @Valid @Min(0) Long id, @RequestParam String name) {
        actorService.updateActorName(id, name);
        return new ResponseEntity<>("Actor name updated", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete an actor")
    public ResponseEntity<String> deleteActor(@ApiParam(value = "id of the actor you want to delete", required = true)
                                              @PathVariable @Valid @Min(0) Long id) {
        actorService.deleteActor(id);
        return new ResponseEntity<>("Actor deleted", HttpStatus.OK);
    }

    @GetMapping("movies/{id}")
    @ApiOperation(value = "Retrieve all actors from a specified movie",
            response = ActorDto.class,
            responseContainer = "Set")
    public Set<ActorDto> getAllActorsFromMovie(@ApiParam(value = "id of the movie", required = true)
                                               @PathVariable @Valid @Min(0) Long id) {
        return actorService.getActorsFromMovie(id);
    }
}
