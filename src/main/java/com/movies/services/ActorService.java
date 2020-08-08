package com.movies.services;

import com.movies.dto.ActorDto;
import com.movies.entities.Actor;
import com.movies.entities.Movie;
import com.movies.exceptions.DuplicateEntityException;
import com.movies.exceptions.EntityNotFoundException;
import com.movies.repositories.ActorRepository;
import com.movies.repositories.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    public List<ActorDto> getAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(Collectors.toList());
    }

    public List<ActorDto> getActorsFromMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie", movieId));

        return movie.getActors()
                .stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(Collectors.toList());
    }

    private boolean checkIfActorExists(Long id) {
        return actorRepository.findById(id).isPresent();
    }

    public void addActor(ActorDto actorDto) {
        Long actorId = actorDto.getId();
        if (actorId != null && checkIfActorExists(actorId)) {
            throw new DuplicateEntityException("Actor", actorId);
        }
        actorRepository.save(modelMapper.map(actorDto, Actor.class));
    }

    public void updateActorName(Long id, String name) {
        if (!checkIfActorExists(id)) {
            throw new EntityNotFoundException("Actor", id);
        }
        actorRepository.updateName(id, name);
    }

    public void deleteActor(Long id) {
        if (!checkIfActorExists(id)) {
            throw new EntityNotFoundException("Actor", id);
        }
        actorRepository.deleteById(id);
    }
}
