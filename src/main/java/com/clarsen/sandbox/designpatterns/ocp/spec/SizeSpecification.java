package com.clarsen.sandbox.designpatterns.ocp.spec;

import com.clarsen.sandbox.designpatterns.ocp.enums.Size;
import com.clarsen.sandbox.designpatterns.ocp.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeSpecification implements Specification<Product> {
    private Size size;

    @Override
    public boolean isSatisfied(Product item) {
        return item.getSize().equals(size);
    }
}
