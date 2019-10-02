package com.training.java.model.service;

import com.training.java.model.dao.DaoFactory;
import com.training.java.model.dao.OrderDishDao;
import com.training.java.model.entity.OrderDish;

import java.util.List;
import java.util.Optional;

public class OrderDishService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    public void save(OrderDish orderDish) {
        try (OrderDishDao dao = daoFactory.createOrderDishDao()) {
            dao.save(orderDish);
        }
    }

    public List<Integer> confirmToAdmin() {
        try (OrderDishDao dao = daoFactory.createOrderDishDao()) {
            return dao.findOrderUncheckedIndex();
        }
    }

    public void confirm(int indOrder) {
        try (OrderDishDao dao = daoFactory.createOrderDishDao()) {
            dao.setChecked(indOrder);
        }
    }

    public List<Integer> confirmToUser(int id) {
        try (OrderDishDao dao = daoFactory.createOrderDishDao()) {
            return dao.findUnConfirmed(id);
        }
    }

    public Optional<OrderDish> getByOrderId(int id) {
        try (OrderDishDao dao = daoFactory.createOrderDishDao()) {
            return dao.getByOrderID(id);
        }
    }

    public void payed(int indOrder) {
        try (OrderDishDao dao = daoFactory.createOrderDishDao()) {
            dao.setPayed(indOrder);
        }
    }

}
