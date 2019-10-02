package com.training.java.controller.commands;

import com.training.java.model.entity.Product;
import com.training.java.model.service.OrderDishService;
import com.training.java.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminOrder implements Command {
    private ProductService productService;
    private OrderDishService orderDishService;

    public AdminOrder(ProductService productService, OrderDishService orderDishService) {
        this.productService = productService;
        this.orderDishService = orderDishService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("ind", orderDishService.confirmToAdmin());
        if (productService.findAllProducts().stream().anyMatch(s -> s.getAmountHave() < s.getMinAmount())) {
            List<Product> products = productService.findAllProducts();
            for (Product product : products) {
                product.setAmountHave(product.getAmountHave() + product.getProductInBox() *
                        ((product.getMaxAmount() - product.getAmountHave()) / product.getProductInBox()));
                productService.update(product);
            }
        }
        request.setAttribute("products", productService.findAllProducts());
        return "/WEB-INF/views/adminOrder.jsp";
    }
}
