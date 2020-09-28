package com.movies.services;

import com.movies.dto.ActorDto;
import com.movies.entities.Movie;
import com.movies.exceptions.EntityNotFoundException;
import com.movies.mappers.ActorMapper;
import com.movies.repositories.ActorRepository;
import com.movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ActorMapper actorMapper;

    public List<ActorDto> getAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(actorMapper::actorToActorDto)
                .collect(Collectors.toList());
    }

    public List<ActorDto> getActorsFromMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie", movieId));

        return movie.getActors()
                .stream()
                .map(actorMapper::actorToActorDto)
                .collect(Collectors.toList());
    }

    public void addActor(ActorDto actorDto) {
        actorRepository.save(actorMapper.actorDtoToActor(actorDto));
    }

    public void updateActorName(Long id, String name) {
        actorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Actor", id));
        actorRepository.updateName(id, name);
    }

    public void deleteActor(Long id) {
        actorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Actor", id));
        actorRepository.deleteById(id);
    }
}
