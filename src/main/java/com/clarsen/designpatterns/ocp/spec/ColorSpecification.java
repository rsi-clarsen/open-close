package com.clarsen.designpatterns.ocp.spec;

import com.clarsen.designpatterns.ocp.enums.Color;
import com.clarsen.designpatterns.ocp.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorSpecification implements Specification<Product> {
    private Color color;

    @Override
    public boolean isSatisfied(Product item) {
        return item.getColor().equals(color);
    }
}
