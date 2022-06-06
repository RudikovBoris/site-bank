package comNew.mySite.site.controllers.bankContollers.moderator;

import comNew.mySite.site.controllers.bankContollers.admin.Accounts;
import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank/moderator/")
public class ModeratorDeleteAccount {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping( "accountDelete")
    public String getAccountBlock(@RequestParam(value = "numberAccount", required = false) Long numberAccount, @RequestParam(value = "idUser", required = false) Long idUser, @RequestParam(value = "idAccount", required = false) Long idAccount, Model model){
        Optional<User> user = userRepository.findById(idUser);
        List<Accounts> accounts = accountRepository.findByIdUser(idUser);
        accounts.get(numberAccount.intValue()-1).setMoney(new BigDecimal(0.00));
        accounts.get(numberAccount.intValue()-1).setAccountNonLocked(false);
        accountRepository.save(accounts.get(numberAccount.intValue()-1));
        List<Accounts> accounts2 = accountRepository.findByIdUser(idUser);
        model.addAttribute("idAccount",numberAccount);
        model.addAttribute("idUser",idUser);
        model.addAttribute("accounts",accounts);
        model.addAttribute("user", user.get());
        return "bank/moderator/checkUser";
    }

}
