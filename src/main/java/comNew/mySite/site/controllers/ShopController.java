package comNew.mySite.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    @GetMapping ("/shop")
    public String shopMain (Model model ){
        model.addAttribute("title","Shop");
        return "shop";
    }
}
