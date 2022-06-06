package comNew.mySite.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/game")
    public String homeGame (Model model){
        model.addAttribute("title", "Game");
        return "game";
    }
}
