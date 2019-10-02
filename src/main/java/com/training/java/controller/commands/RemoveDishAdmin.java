package com.training.java.controller.commands;

import com.training.java.model.service.DishService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class RemoveDishAdmin implements Command {
    private DishService dishService;

    public RemoveDishAdmin(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String[] ds = request.getParameterValues("ds");
        int[] idArr = Arrays.stream(ds).mapToInt(Integer::parseInt).toArray();
        for (int id : idArr) {
            dishService.deleteByID(id);
        }
        return "redirect:/add";
    }

}
