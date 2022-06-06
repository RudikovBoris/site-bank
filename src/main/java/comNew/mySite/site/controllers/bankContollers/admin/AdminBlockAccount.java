package comNew.mySite.site.controllers.bankContollers.admin;

import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminBlockAccount {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping( "accountBlock")
    public String getAccountBlock(@RequestParam ("idUser") Long idUser,@RequestParam("numberAccount") Long numberAccount, Model model){
        model.addAttribute("idAccount",numberAccount);
        model.addAttribute("idUser",idUser);
        return "bank/admin/adminAccountBlock";
    }


    @GetMapping("accountBlock1")
    public String postAccountBlock2(@RequestParam ("idUser") Long idUser,@RequestParam ("numberAccount") Integer numberAccount, Model model){
        Optional<User> user = userRepository.findById(idUser);

        List<Accounts> accounts = accountRepository.findByIdUser(idUser);
        accounts.get(numberAccount-1).setAccountNonLocked(true);
        accountRepository.save(accounts.get(numberAccount-1));
        model.addAttribute("idAccount",numberAccount);
        model.addAttribute("idUser",idUser);
            model.addAttribute("accountFull",accounts);

        model.addAttribute("userFull", user.get());
        return "bank/admin/adminUserIsFound";
    }
    @GetMapping("accountBlock2")
    public String postAccountBlock1(@RequestParam ("idUser") Long idUser,@RequestParam ("numberAccount") Integer numberAccount, Model model){
        Optional<User> user = userRepository.findById(idUser);

        List<Accounts> accounts = accountRepository.findByIdUser(idUser);
        accounts.get(numberAccount-1).setAccountNonLocked(false);
        accountRepository.save(accounts.get(numberAccount-1));
        model.addAttribute("idAccount",numberAccount);
        model.addAttribute("idUser",idUser);
            model.addAttribute("accountFull",accounts);

        model.addAttribute("userFull", user.get());
        return "bank/admin/adminUserIsFound";
    }
}
