package com.training.java.model.dao;

import com.training.java.controller.utils.BankTransactionException;
import com.training.java.model.entity.OrderDish;
import com.training.java.model.entity.UserAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDishDao extends GenericDao<OrderDish> {
    public void save(OrderDish orderDish);

    public List<Integer> findOrderUncheckedIndex();

    public void setChecked(int ind);

    public List<Integer> findUnConfirmed(int id);

    public Optional<OrderDish> getByOrderID(int id);

    public void setPayed(int id);

    public void payTheOrder(UserAccount user, UserAccount restaurant, BigDecimal sum) throws BankTransactionException;
}
