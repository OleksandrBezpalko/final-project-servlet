package com.training.java.controller.commands;

import com.training.java.model.service.OrderDishService;

import javax.servlet.http.HttpServletRequest;

public class Confirm implements Command {
    private OrderDishService orderDishService;

    public Confirm(OrderDishService orderDishService) {
        this.orderDishService = orderDishService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("ind"));
        orderDishService.confirm(id);
        return "redirect:/adminOrder";
    }
}
