package pblweek2.megazine.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pblweek2.megazine.security.UserDetailsImpl;

//@Controller
//public class HomeController {
//    @GetMapping("/")
//    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails != null){
//            model.addAttribute("username", userDetails.getUsername());
//        }
//        return "home";
//    }
//}
