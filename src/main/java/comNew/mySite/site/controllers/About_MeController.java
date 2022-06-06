package comNew.mySite.site.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class About_MeController {
    @GetMapping ("/about_Me")
    public String about_MeMain (Model model){
        model.addAttribute("title" , "about me");


        return "about_Me";
    }
}
