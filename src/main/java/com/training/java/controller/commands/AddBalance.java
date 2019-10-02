package com.training.java.controller.commands;

import com.training.java.model.entity.UserAccount;
import com.training.java.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class AddBalance implements  Command {
    private UserService userService;

    public AddBalance(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<String>  toAdd=Optional.ofNullable(request.getParameter("toAdd"));
        if(!toAdd.isPresent()||toAdd.get().equals("")||toAdd.get().length()>5||toAdd.get().charAt(0)=='-'){
            return "redirect:/addMoney";
        }
        BigDecimal moneyToAdd=BigDecimal.valueOf(Double.valueOf(toAdd.get()));
        Optional<UserAccount> loginedUser =Optional.ofNullable((UserAccount) request.getSession()
                .getAttribute("loginedUser"));
        Optional<String> name = Optional.empty();;
        if (loginedUser.isPresent()) {
            name = Optional.of(loginedUser.get().getUserName());
        }

        UserAccount user = userService.findByUsername(name.get()).get();
        user.setMoneyHave(user.getMoneyHave().add(moneyToAdd));
        userService.update(user);
        return "redirect:/addMoney";
    }
}
