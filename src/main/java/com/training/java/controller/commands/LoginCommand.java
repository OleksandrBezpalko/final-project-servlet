package com.training.java.controller.commands;

import com.training.java.controller.utils.AppUtils;
import com.training.java.controller.utils.Password;
import com.training.java.model.entity.UserAccount;
import com.training.java.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        Optional<UserAccount> userAccount = userService.findByUsername(userName);
        if (userAccount.isPresent() && CommandUtility.checkUserIsLogged(request, userName)) {
            String errorMessage = "User already logged";
            request.setAttribute("errorString1", errorMessage);

            return "/WEB-INF/views/loginView.jsp";
        }
        try {
            if (!userAccount.isPresent() || !Password.check(password, userAccount.get().getPassword())) {
                if (userName != null) {
                    request.setAttribute("errorString", "invalid");
                }

                return "/WEB-INF/views/loginView.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().invalidate();
        AppUtils.storeLoginedUser(request.getSession(), userAccount.get());

        String requestUri = AppUtils.getRedirectAfterLoginUrl();
        if (requestUri != null) {
            return "redirect:" + requestUri;
        } else {
            return "redirect:/menu";
        }
    }
}
