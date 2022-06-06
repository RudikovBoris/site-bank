package comNew.mySite.site.controllers.bankContollers.admin;

import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/bank/admin/")
public class AdminDeleteAccount {
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
        for (int i = 0; i < accounts2.size(); i++) {
            model.addAttribute("accountFull"+i,accounts2.get(i));
        }
        model.addAttribute("userFull", user.get());
        return "bank/admin/adminUserIsFound";
    }
}
