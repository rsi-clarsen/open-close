package com.clarsen.designpatterns.ocp.model;

import com.clarsen.designpatterns.ocp.enums.Color;
import com.clarsen.designpatterns.ocp.enums.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private String name;
    private Color color;
    private Size size;
}
