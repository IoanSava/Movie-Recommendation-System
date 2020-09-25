package com.movies.mappers;

import com.movies.dto.ActorDto;
import com.movies.entities.Actor;
import org.springframework.stereotype.Component;

@Component
public final class ActorMapper {
    public Actor actorDtoToActor(ActorDto actorDto) {
        Actor actor = new Actor();
        actor.setName(actorDto.getName());
        actor.setAge(actorDto.getAge());
        return actor;
    }

    public ActorDto actorToActorDto(Actor actor) {
        ActorDto actorDto = new ActorDto();
        actorDto.setName(actor.getName());
        actorDto.setAge(actor.getAge());
        return actorDto;
    }
}