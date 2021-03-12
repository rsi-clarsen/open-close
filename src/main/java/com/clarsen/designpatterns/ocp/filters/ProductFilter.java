package com.clarsen.designpatterns.ocp.filters;

import java.util.List;
import java.util.stream.Stream;

import com.clarsen.designpatterns.ocp.enums.Color;
import com.clarsen.designpatterns.ocp.enums.Size;
import com.clarsen.designpatterns.ocp.model.Product;

public class ProductFilter {

    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> p.getColor().equals(color));
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> p.getSize().equals(size));
    }
}
