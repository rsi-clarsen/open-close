package com.clarsen.designpatterns.ocp.spec;

public interface Specification<T> {
    boolean isSatisfied(T item);
}
