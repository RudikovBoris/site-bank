package comNew.mySite.site.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideoController {

    @GetMapping ("/video")
    public String videoMain( Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.get("key=");
        model.addAttribute("title","Video");
        return "video";
    }
}
