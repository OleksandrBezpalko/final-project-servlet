package com.training.java.controller.commands;

import com.training.java.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AddMoney implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        Menu menu = new Menu(new UserService());
        menu.loginedUser(request);
        return "/WEB-INF/views/addBalance.jsp";
    }


}
