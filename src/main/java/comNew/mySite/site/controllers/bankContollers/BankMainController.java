package comNew.mySite.site.controllers.bankContollers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@EnableWebSecurity
@RequestMapping("/bank")
public class BankMainController {

    @GetMapping ("/bankMain")
    public String bankMain (Model model){
        return "bank/bankMainHTML";
    }

}