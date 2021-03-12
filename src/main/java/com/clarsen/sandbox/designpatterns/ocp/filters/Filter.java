package com.clarsen.sandbox.designpatterns.ocp.filters;

import java.util.List;
import java.util.stream.Stream;

import com.clarsen.sandbox.designpatterns.ocp.spec.Specification;

public interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}
