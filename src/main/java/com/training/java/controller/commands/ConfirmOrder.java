package com.training.java.controller.commands;

import com.training.java.model.entity.OrderDish;
import com.training.java.model.entity.Product;
import com.training.java.model.service.OrderDishService;
import com.training.java.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

public class ConfirmOrder implements Command {
    private OrderDishService orderDishService;
    private ProductService productService;

    public ConfirmOrder(OrderDishService orderDishService, ProductService productService) {
        this.orderDishService = orderDishService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<OrderDish> orderDish = Optional.of((OrderDish) session.getAttribute("orderDish"));

        Map<Product, Integer> enoughProducts = new OrderController()
                .enoughProducts(orderDish.get());

        if (enoughProducts.keySet().stream()
                .anyMatch(s -> (productService.getAmountHave(s) - enoughProducts.get(s)) < 0)) {
            return "redirect:/order";
        }


        orderDish.get().setToAdmin(true);
        orderDishService.save(orderDish.get());

        session.setAttribute("orderDish",null);
        return "redirect:/menu";
    }
}
