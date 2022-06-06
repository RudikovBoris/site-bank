package comNew.mySite.site.controllers.bankContollers.admin;


import comNew.mySite.site.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bank/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminChangeAccountUser {

    Accounts accounts;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("changeAccount")
    public String changeAccount(@RequestParam("numberAccount") Long numberAccount,  Model model){
       accounts = accountRepository.getById(numberAccount);
        model.addAttribute("money",accounts.getMoney());
        model.addAttribute("idAccount",accounts.getId());

        model.addAttribute("numberAccount",accounts.getNumberAccount());
        return  "bank/admin/adminChangeAccountChoice";
    }

}
