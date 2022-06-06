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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank/moderator/")
public class ModeratorCheckUser {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("user")
    public String checkUser (@RequestParam ("numberAccount") Long numberAccount, Model model){
        Optional<User> user = userRepository.findById(numberAccount);
        List<Accounts> accountsList = accountRepository.findByIdUser(user.get().getId());
        model.addAttribute("user", user.get());
        model.addAttribute("accounts",accountsList);
        return "bank/moderator/checkUser";
    }
}
