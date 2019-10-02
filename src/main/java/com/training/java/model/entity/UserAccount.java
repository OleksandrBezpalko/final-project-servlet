package com.training.java.model.entity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {

    private int id;
    private String username;
    private String lastName;
    private String password;
    private BigDecimal moneyHave = BigDecimal.valueOf(0);
    private List<String> roles;
    private Product prodLikeNow;

    public Product getProdLikeNow() {
        return prodLikeNow;
    }

    public void setProdLikeNow(Product prodLikeNow) {
        this.prodLikeNow = prodLikeNow;
    }

    public UserAccount() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getMoneyHave() {
        return moneyHave;
    }

    public void setMoneyHave(BigDecimal moneyHave) {
        this.moneyHave = moneyHave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserAccount(String userName, String password, String lastName, String... roles) {
        this.username = userName;
        this.password = password;
        this.lastName = lastName;

        this.roles = new ArrayList<String>();
        if (roles != null) {
            for (String r : roles) {
                this.roles.add(r);
            }
        }
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}