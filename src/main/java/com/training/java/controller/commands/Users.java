package com.training.java.controller.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.training.java.model.entity.UserAccount;
import com.training.java.model.service.UserService;

public class Users implements Command {
    private UserService userService;

    public Users(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {


        request.setAttribute("users", userService.getAllUsers());
        String[] arr = request.getParameterValues("roles");
        Optional<String> ind = Optional.ofNullable(request.getParameter("id"));

        if(ind.isPresent()){
            int id = Integer.parseInt(ind.get());
            Optional<UserAccount> userAccount = userService.findById(id);
            if (userAccount.isPresent()){
                userAccount.get().setRoles(new ArrayList<>());
                if (arr != null)
                    Arrays.stream(arr).forEach(s-> userAccount.get().getRoles().add(s));
                if(!userAccount.get().getRoles().contains("USER")){
                    userAccount.get().getRoles().add("USER");
                }
                userService.updateRoles(userAccount.get());
                return "redirect:/user";
            }
        }

        return "/WEB-INF/views/users.jsp";
    }
}
