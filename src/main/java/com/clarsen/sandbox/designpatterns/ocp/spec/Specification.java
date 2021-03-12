package com.clarsen.sandbox.designpatterns.ocp.spec;

public interface Specification<T> {
    boolean isSatisfied(T item);
}
