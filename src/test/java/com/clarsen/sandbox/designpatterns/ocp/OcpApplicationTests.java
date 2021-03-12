package com.clarsen.sandbox.designpatterns.ocp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import com.clarsen.sandbox.designpatterns.ocp.enums.Color;
import com.clarsen.sandbox.designpatterns.ocp.enums.Size;
import com.clarsen.sandbox.designpatterns.ocp.filters.OCPProductFilter;
import com.clarsen.sandbox.designpatterns.ocp.filters.ProductFilter;
import com.clarsen.sandbox.designpatterns.ocp.model.Product;
import com.clarsen.sandbox.designpatterns.ocp.spec.AndSpecification;
import com.clarsen.sandbox.designpatterns.ocp.spec.ColorSpecification;
import com.clarsen.sandbox.designpatterns.ocp.spec.SizeSpecification;

import org.junit.jupiter.api.Test;

class OcpApplicationTests {

    private final Product APPLE = buildProduct("apple", Color.GREEN, Size.SMALL);
    private final Product TREE = buildProduct("tree", Color.GREEN, Size.LARGE);
    private final Product HOUSE = buildProduct("house", Color.BLUE, Size.LARGE);

    private final List<Product> products = List.of(APPLE, TREE, HOUSE);

    @Test
    void testNonOCP() {
        ProductFilter pf = new ProductFilter();

        List<Product> underTest = pf.filterByColor(products, Color.GREEN)
            .collect(Collectors.toList());

        assertTrue(underTest.size() == 2);
        assertTrue(underTest.contains(APPLE));
        assertTrue(underTest.contains(TREE));
    }

    @Test
    void testOCP() {
        OCPProductFilter ocpFilter = new OCPProductFilter();
        ColorSpecification greenColorSpec = new ColorSpecification(Color.GREEN);

        List<Product> underTest = ocpFilter.filter(products, greenColorSpec)
            .collect(Collectors.toList());

        assertTrue(underTest.size() == 2);
        assertTrue(underTest.contains(APPLE));
        assertTrue(underTest.contains(TREE));
    }

    @Test
    void testOCPWithMultiSpec() {
        OCPProductFilter ocpFilter = new OCPProductFilter();

        AndSpecification<Product> spec = new AndSpecification<>(
            new ColorSpecification(Color.BLUE),
            new SizeSpecification(Size.LARGE));

        List<Product> underTest = ocpFilter.filter(products, spec)
            .collect(Collectors.toList());

        assertTrue(underTest.size() == 1);
        assertTrue(underTest.contains(HOUSE));
    }

    private static Product buildProduct(String name, Color color, Size size) {
        return Product.builder()
                    .name(name)
                    .color(color)
                    .size(size)
                    .build();
    }
}
