package com.training.java.model.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Dish implements Comparable<Dish> {
    /**
     * unique parametr
     */
    private int id;
    /**
     * String max length of 500 used for link of image
     */
    private String fileName;

    /**
     * name of dish used in ukr
     */
    private String nameUkr;
    /**
     * name used for default locate
     */
    private String name;
    /**
     * BigDecemical used to save price of dish
     */
    private BigDecimal price;
    /**
     * All products which dish contains
     */
    private List<Product> productsForDish;

    /**
     * @return products
     */
    public List<Product> getProductsForDish() {
        return productsForDish;
    }

    /**
     * @param productsForDish all products in this dish
     */
    public void setProductsForDish(List<Product> productsForDish) {
        this.productsForDish = productsForDish;
    }

    public Dish(int id, String fileName, String nameUkr, String name, BigDecimal price, List<Product> productsForDish) {
        this.id = id;
        this.fileName = fileName;
        this.nameUkr = nameUkr;
        this.name = name;
        this.price = price;
        this.productsForDish = productsForDish;
    }

    public Dish() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish dish = (Dish) o;
        return getId() == dish.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * @param o
     * used to compare to dishes in their alphabetical order
     * @return int of compare 0 if similar
     * less zero if first less
     * more then zero if second arg less
     */
    @Override
    public int compareTo(Dish o) {
        return this.name.compareTo(o.getName());
    }
}
