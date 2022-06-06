package comNew.mySite.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationController {

    @GetMapping ("/information")
    public String informationMain (Model model){
        model.addAttribute("title" , "Information");
        return "information";
    }
}
