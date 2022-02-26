package pblweek2.megazine.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pblweek2.megazine.exception.AlreadyLoggedinException;
import pblweek2.megazine.security.UserDetailsImpl;

@Controller
public class PageController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null){
            model.addAttribute("username", userDetails.getUsername());
        }
        return "home";
    }

    @GetMapping("/user/login")
    public String getLoginPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            throw new AlreadyLoggedinException();
        }
        return "login";
    }

    @GetMapping("/user/signup")
    public String getSignupPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            throw new AlreadyLoggedinException();
        }
        return "signup";
    }
}
