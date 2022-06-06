package comNew.mySite.site.controllers.bankContollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankCardDateController {

    @GetMapping("/bank/card")
    public String cardInBase(Model model ){

        return "bank/bankCardDate";
    }
}
