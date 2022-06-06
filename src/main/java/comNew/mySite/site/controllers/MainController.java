package comNew.mySite.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    public String greetings(Model model){
        model.addAttribute("title", "Home");
        return "home";
    }

}
