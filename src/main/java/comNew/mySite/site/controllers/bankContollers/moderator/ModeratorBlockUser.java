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
public class ModeratorBlockUser {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("userBlock")
    public String getAccountBlock(@RequestParam("idUser") Long idUser, Model model) {
        Optional<User> user = userRepository.findById(idUser);
        model.addAttribute("user", user.get());
        model.addAttribute("userName", user.get().getUsername());
        return "bank/moderator/moderatorBlockUser";
    }

    @GetMapping("userBlock1")
    public String postAccountBlock2(@RequestParam ("idUser") Long idUser, Model model){
        Optional<User> user = userRepository.findById(idUser);
        user.get().setAccountNonLocked(true);
        userRepository.save(user.get());
        model.addAttribute("idUser",idUser);
        model.addAttribute("user", user.get());
        model.addAttribute("users",userRepository.findAll());
        return "bank/moderator/moderator";
    }
    @GetMapping("userBlock2")
    public String postAccountBlock1(@RequestParam ("idUser") Long idUser, Model model){
        Optional<User> user = userRepository.findById(idUser);
        user.get().setAccountNonLocked(false);
        userRepository.save(user.get());
        model.addAttribute("idUser",idUser);
        model.addAttribute("user", user.get());
        model.addAttribute("users",userRepository.findAll());
        return "bank/moderator/moderator";
    }
}
