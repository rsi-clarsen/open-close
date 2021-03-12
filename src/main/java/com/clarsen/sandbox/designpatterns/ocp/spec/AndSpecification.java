package com.clarsen.sandbox.designpatterns.ocp.spec;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AndSpecification<T> implements Specification<T> {
    private Specification<T> spec1, spec2;

    @Override
    public boolean isSatisfied(T item) {
        return Stream.of(spec1, spec2).allMatch(s -> s.isSatisfied(item));
    }
}
