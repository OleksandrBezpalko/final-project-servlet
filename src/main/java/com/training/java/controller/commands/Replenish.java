package com.training.java.controller.commands;

import com.training.java.model.entity.Product;
import com.training.java.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Replenish implements Command {
    private ProductService productService;

    public Replenish(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Product> products = productService.findAllProducts();
        for (Product product : products) {
            product.setAmountHave(product.getAmountHave() + product.getProductInBox() *
                    ((product.getMaxAmount() - product.getAmountHave()) / product.getProductInBox()));

            productService.update(product);
        }

        return "redirect:/adminOrder";
    }
}
