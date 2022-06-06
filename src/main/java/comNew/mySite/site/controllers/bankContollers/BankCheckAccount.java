package comNew.mySite.site.controllers.bankContollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller()
public class BankCheckAccount {

    @GetMapping("/bank/bankMain/checkAccount")
    public String checkAccountHtml(Model model){
        return "bank/bankCheckAccount";
    }

    @GetMapping("/{accountId}")
    public BigDecimal getBalance(@PathVariable long accountId){

     BigDecimal a=new BigDecimal("2.5");
     return a;
    }

    @PostMapping("/{accountId}")
    public BigDecimal addMoney(@PathVariable Long accountId,@RequestBody BigDecimal amount){
        BigDecimal a=new BigDecimal("2.5");
        return a;

    }


}
