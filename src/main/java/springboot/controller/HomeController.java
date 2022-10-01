package springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

//    @RequestMapping(value={"", "/", "home"})
//    public String displayHomePage() {
//        return "home.html";
//    }

    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage(Model model) {
        model.addAttribute("username", "John Doe4");
        return "home.html";
    }

}
