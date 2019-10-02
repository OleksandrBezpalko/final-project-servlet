package com.training.java.controller.commands;

import com.training.java.model.entity.Dish;
import com.training.java.model.entity.Product;
import com.training.java.model.service.DishService;
import com.training.java.model.service.ProductService;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddDish implements Command {
    private ProductService  productService;
    private DishService dishService ;

    public AddDish(ProductService productService, DishService dishService) {
        this.productService = productService;
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Optional<String>  fileName = Optional.ofNullable(request.getParameter("fileName"));
        Optional<String> nameUkr = Optional.ofNullable(request.getParameter("nameUkr"));
        Optional<String> name = Optional.ofNullable(request.getParameter("name"));
        Optional<String>  price =Optional.ofNullable( request.getParameter("price"));
        Optional<String[]>  prod =Optional.ofNullable(request.getParameterValues("prod"));
        request.setAttribute("products", productService.findAllProducts());
        request.setAttribute("dishes",dishService.findAll());
        if(!fileName.isPresent() ||fileName.get().equals("")|| !name.isPresent()|| !nameUkr.isPresent()
                || !price.isPresent()||price.get().equals("")
                || !prod.isPresent() ||fileName.get().length()>255){
            if(name.isPresent()){
                request.setAttribute("error","errorData");
            }

            return "/WEB-INF/views/addDish.jsp";
        }
        Dish newDish = new Dish();
        newDish.setFileName(fileName.get());
        double priceD=Double.valueOf(price.get());
        newDish.setPrice(BigDecimal.valueOf(priceD));
        newDish.setNameUkr(nameUkr.get());
        newDish.setName(name.get());
        List<Product> prodTemp = new CopyOnWriteArrayList<>();
        Arrays.stream(prod.get()).forEach(s->prodTemp.add(productService.getByProductName(s).get()));
        newDish.setProductsForDish(prodTemp);
        try {
            dishService.saveDish(newDish);
        }catch (SQLIntegrityConstraintViolationException e){
            request.setAttribute("error","errorData");
            return "/WEB-INF/views/addDish.jsp";
        }


        return "redirect:/menu";
    }
}
