package com.training.java.controller.commands;

import javax.servlet.http.HttpServletRequest;
public class LogOut implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/";
    };
}
