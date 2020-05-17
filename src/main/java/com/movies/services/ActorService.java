package com.movies.services;


import com.movies.dto.ActorDto;
import com.movies.entities.Actor;
import com.movies.exceptions.DuplicateEntityException;
import com.movies.exceptions.EntityNotFoundException;
import com.movies.exceptions.NoDataFoundException;
import com.movies.repositories.ActorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<ActorDto> getAllActors() {
        List<ActorDto> actors = ((List<Actor>) actorRepository.findAll())
                .stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(Collectors.toList());

        if (actors.size() == 0) {
            throw new NoDataFoundException();
        }

        return actors;
    }

    private boolean checkIfActorExists(Long id) {
        Optional<Actor> actor = actorRepository.findById(id);
        return actor.isPresent();
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
