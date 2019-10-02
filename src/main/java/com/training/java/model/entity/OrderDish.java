package com.training.java.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class OrderDish {
    private int id;

    List<Dish> dishes;
    private boolean checked;
    private boolean toAdmin;
    private boolean payed;
    private BigDecimal priceAll;
    private UserAccount user;

    public OrderDish() {
    }

    public OrderDish(int id, List<Dish> dishes,
                     boolean checked, boolean toAdmin, boolean payed,
                     BigDecimal priceAll, UserAccount user) {
        this.id = id;
        this.dishes = dishes;
        this.checked = checked;
        this.toAdmin = toAdmin;
        this.payed = payed;
        this.priceAll = priceAll;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OrderDish{" +
                "id=" + id +
                ", dishes=" + dishes +
                ", checked=" + checked +
                ", toAdmin=" + toAdmin +
                ", payed=" + payed +
                ", priceAll=" + priceAll +
                ", user=" + user +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isToAdmin() {
        return toAdmin;
    }

    public void setToAdmin(boolean toAdmin) {
        this.toAdmin = toAdmin;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public BigDecimal getPriceAll() {
        return priceAll;
    }

    public void setPriceAll(BigDecimal priceAll) {
        this.priceAll = priceAll;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }
}
