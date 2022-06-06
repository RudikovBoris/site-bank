package comNew.mySite.site.controllers.bankContollers.user;

import comNew.mySite.site.controllers.bankContollers.admin.Accounts;
import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank/user/")
public class UserSendMoney {
    Accounts accounts;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;
    @GetMapping("changeAccount")
    public String choiceAccount(@RequestParam("numberAccount") Long numberAccount, Model model){
        if(!accountRepository.findById(numberAccount).get().getAccountNonLocked()){
            Optional<Accounts> account = accountRepository.findById(numberAccount);
            Long userId = account.get().getIdUser();
            Optional<User> user = userRepository.findById(userId);
            List<Accounts> accounts = accountRepository.findByIdUser(user.get().getId());
            model.addAttribute("accounts",accounts);
            model.addAttribute("user", user.get());

            return "bank/user/exception/userExceptionAccountLocked";
        }
        accounts = accountRepository.getById(numberAccount);
        model.addAttribute("money",accounts.getMoney());
        model.addAttribute("idAccount",accounts.getId());

        model.addAttribute("numberAccount",accounts.getNumberAccount());
        return  "bank/user/userSendMoneyChoice";
    }



}
