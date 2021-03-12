# Open Close Principle

The ***open-close principle*** means that any class is open to extension but closed to modification.  Meaning that if we already have a class that has been created, tested, and deployed then we should try to stay away from changing it.

Imagine working on an online retail application that sales `Product`s.  The project's product owner wants to add the ability to `Filter` a customer's products by their `Color`.  The `ProductFilter` class is created, unit tested, and deployed.  Afterwards the product owner wants to add a second filter for the product's `Size`.  Easy enough to add a new method to the `ProductFilter` class previously created to handle this logic.  Since the `ProductFilter` class is already created, tested, and being utilized by others using our application, this violates the open-close principle.

Coupling the open-close principle with the ***Specification*** enterprise design pattern, we can create individual `Filter`s to satisfy the product owner.

## Product Model
[Color](src/main/java/com/clarsen/sandbox/designpatterns/ocp/enums/Color.java): basic enum to supply values to filter by
```
public enum Color {
    RED, BLUE, GREEN
}
```

[Size](src/main/java/com/clarsen/sandbox/designpatterns/ocp/enums/Size.java): basic enum to supply values to filter by
```
public enum Size {
    SMALL, MEDIUM, LARGE, HUGE
}
```

[Product](src/main/java/com/clarsen/sandbox/designpatterns/ocp/model/Product.java): model containing 3 member fields
```
public class Product {
    private String name;
    private Color color;
    private Size size;
}
```

## Non OCP Solution
[ProductFilter](src/main/java/com/clarsen/sandbox/designpatterns/ocp/filters/ProductFilter.java)
```
public class ProductFilter {

    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> p.getColor().equals(color));
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> p.getSize().equals(size));
    }
}
```

Given a list of products as:
```
private final Product APPLE = buildProduct("apple", Color.GREEN, Size.SMALL);
private final Product TREE = buildProduct("tree", Color.GREEN, Size.LARGE);
private final Product HOUSE = buildProduct("house", Color.BLUE, Size.LARGE);

private final List<Product> products = List.of(APPLE, TREE, HOUSE);
```

the ***products*** list can be filtered by using the proper method.

```
ProductFilter pf = new ProductFilter();

List<Product> underTest = pf.filterByColor(products, Color.GREEN)
    .collect(Collectors.toList());

assertTrue(underTest.size() == 2);
assertTrue(underTest.contains(APPLE));
assertTrue(underTest.contains(TREE));
```

Although this works, the product filter violates the open close priciple because code needs to be added to a class that has already been created and tested.  Any new filtering logic would need to create another member function.

## OCP Solution

Can solve the ocp problem with help from the **specification** pattern.

First, create a generic interface to determin if a given object satisfies a specific rule.  

[Specfication](src/main/java/com/clarsen/sandbox/designpatterns/ocp/spec/Specification.java):
```
public interface Specification<T> {
    boolean isSatisfied(T item);
}
```

Next, split the `ProductFilter` into an interface to define the `Filter` contract and an implementation class to filter `Product`s by a given specification.
```
public interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}
```

```
public class OCPProductFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
    
}
```

Now create the `ColorSpecification` and the `SizeSpecification` to use for the `OCPProductFilter`.  
[ColorSpecification](src/main/java/com/clarsen/sandbox/designpatterns/ocp/spec/ColorSpecification.java):
```
public class ColorSpecification implements Specification<Product> {
    private Color color;

    @Override
    public boolean isSatisfied(Product item) {
        return item.getColor().equals(color);
    }
}
```
[SizeSpecification](src/main/java/com/clarsen/sandbox/designpatterns/ocp/spec/SizeSpecification.java)
```
public class SizeSpecification implements Specification<Product> {
    private Size size;

    @Override
    public boolean isSatisfied(Product item) {
        return item.getSize().equals(size);
    }
}
```

Given the same list of products as:
```
private final Product APPLE = buildProduct("apple", Color.GREEN, Size.SMALL);
private final Product TREE = buildProduct("tree", Color.GREEN, Size.LARGE);
private final Product HOUSE = buildProduct("house", Color.BLUE, Size.LARGE);

private final List<Product> products = List.of(APPLE, TREE, HOUSE);
```

the ***products*** list can be filtered by creating the proper specification:

```
OCPProductFilter ocpFilter = new OCPProductFilter();
ColorSpecification greenColorSpec = new ColorSpecification(Color.GREEN);

List<Product> underTest = ocpFilter.filter(products, greenColorSpec)
    .collect(Collectors.toList());

assertTrue(underTest.size() == 2);
assertTrue(underTest.contains(APPLE));
assertTrue(underTest.contains(TREE));
```

Now the product owner has came back wanting the ability to filter a list of `Product`s by a specific `Color` *and* `Size`.  Now that all the peices have been created, tested, and deployed--we can now create a new `Specification` to give to our `OCPProductFilter`.

[AndSpecification](src/main/java/com/clarsen/sandbox/designpatterns/ocp/spec/AndSpecification.java):
```
public class AndSpecification<T> implements Specification<T> {
    private Specification<T> spec1, spec2;

    @Override
    public boolean isSatisfied(T item) {
        return Stream.of(spec1, spec2).allMatch(s -> s.isSatisfied(item));
    }
}
```

Given the same list of products as:

```
private final Product APPLE = buildProduct("apple", Color.GREEN, Size.SMALL);
private final Product TREE = buildProduct("tree", Color.GREEN, Size.LARGE);
private final Product HOUSE = buildProduct("house", Color.BLUE, Size.LARGE);

private final List<Product> products = List.of(APPLE, TREE, HOUSE);
```

Now the ***products*** list can be filtered by creating and using the new `AndSpecification`:

```
OCPProductFilter ocpFilter = new OCPProductFilter();

AndSpecification<Product> spec = new AndSpecification<>(
    new ColorSpecification(Color.BLUE),
    new SizeSpecification(Size.LARGE));

List<Product> underTest = ocpFilter.filter(products, spec)
    .collect(Collectors.toList());

assertTrue(underTest.size() == 1);
assertTrue(underTest.contains(HOUSE));
```