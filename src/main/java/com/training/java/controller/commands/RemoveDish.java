package com.training.java.controller.commands;

import com.training.java.model.entity.OrderDish;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.IntStream;

public class RemoveDish implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        OrderDish orderDish = (OrderDish) session.getAttribute("orderDish");
        int id = Integer.parseInt(request.getParameter("dishId"));

        IntStream.range(0, orderDish.getDishes().size())
                .filter(s -> orderDish.getDishes().get(s).getId() == id).limit(1)
                .forEach(s -> {
                    orderDish.setPriceAll(orderDish.getPriceAll().subtract(orderDish.getDishes()
                            .get(s).getPrice()));
                    orderDish.getDishes().remove(s);
                });



        session.setAttribute("orderDish", orderDish);
        return "redirect:/order";
    }
}
