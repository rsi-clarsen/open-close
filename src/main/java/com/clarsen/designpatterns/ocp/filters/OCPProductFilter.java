package com.clarsen.designpatterns.ocp.filters;

import java.util.List;
import java.util.stream.Stream;

import com.clarsen.designpatterns.ocp.model.Product;
import com.clarsen.designpatterns.ocp.spec.Specification;

public class OCPProductFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
    
}
