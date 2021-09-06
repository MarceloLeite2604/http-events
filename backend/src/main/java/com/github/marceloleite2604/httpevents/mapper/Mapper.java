package com.github.marceloleite2604.httpevents.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper <I,O> {

    default O mapTo(I object) {
        throw new UnsupportedOperationException();
    }

    default I mapFrom(O object) {
        throw new UnsupportedOperationException();
    }

    default List<O> mapAllTo(Collection<I> objects) {
        return objects.stream()
                .map(this::mapTo)
                .collect(Collectors.toList());
    }

    default List<I> mapAllFrom(Collection<O> objects) {
        return objects.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }
}
