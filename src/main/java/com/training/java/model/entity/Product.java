package com.training.java.model.entity;

import java.util.Objects;

public class Product {
    private int id;
    private String product;
    private int amountHave;
    private int maxAmount;
    private int minAmount;
    private int productInBox;

    public Product(int id, String product, int amountHave, int maxAmount, int minAmount, int productInBox) {
        this.id = id;
        this.product = product;
        this.amountHave = amountHave;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.productInBox = productInBox;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getAmountHave() {
        return amountHave;
    }

    public void setAmountHave(int amountHave) {
        this.amountHave = amountHave;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getProductInBox() {
        return productInBox;
    }

    public void setProductInBox(int productInBox) {
        this.productInBox = productInBox;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product1 = (Product) o;
        return getId() == product1.getId() &&
                getAmountHave() == product1.getAmountHave() &&
                getMaxAmount() == product1.getMaxAmount() &&
                getMinAmount() == product1.getMinAmount() &&
                getProductInBox() == product1.getProductInBox() &&
                Objects.equals(getProduct(), product1.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getAmountHave(), getMaxAmount(), getMinAmount(), getProductInBox());
    }
}
