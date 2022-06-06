package comNew.mySite.site.controllers.bankContollers.admin;

import com.fasterxml.jackson.databind.node.NumericNode;
import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static comNew.mySite.site.controllers.bankContollers.admin.AdminStart.userId;



@Controller
@PreAuthorize("hasRole('ROLE_ADMIN') ")
public class AdminMakeAccount {
    Accounts accounts;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;


    @GetMapping("/bank/admin/newAccount")
    public String adminGetNewAccount(@RequestParam("userIdAccount") Long userIdAccount, Model model) {
        model.addAttribute("userIdAccount",userIdAccount);
        return "bank/admin/adminMakeNewAccount";

    }

    @PostMapping("/bank/admin/newAccount")
    public String adminMakeAccount(@RequestParam ("numberAccount") Long userIdAccount, @RequestParam BigDecimal money, Model model) {
        Optional<User> user = userRepository.findById(userIdAccount);
        BigDecimal biggestMoney = new BigDecimal(999999999999999999999999999999.9999999999);

        if (money.compareTo(biggestMoney) < 0) {

            Accounts newAccount;

            List<Accounts> accounts;


            if (accountRepository.existsByIdUser(userIdAccount)) {
                accounts = accountRepository.findByIdUser(userIdAccount);
                int accountIdUser = accounts.size()+1;
                newAccount = new Accounts(userIdAccount, accountIdUser, money);
                accountRepository.save(newAccount);
                accounts = accountRepository.findByIdUser(userIdAccount);

                    model.addAttribute("accountFull", accounts);



            }else       {

                         Accounts accountsFirstTime = new Accounts(userIdAccount,1,money);
                         accountRepository.save(accountsFirstTime);
                         accounts = accountRepository.findByIdUser(userIdAccount);

                         model.addAttribute("accountFull", accounts);

            }

            model.addAttribute("userFull", user.get());
            return "bank/admin/adminUserIsFound";
        }

        model.addAttribute("userIdAccount",userIdAccount);
        model.addAttribute("userFull", user.get());
        return  "bank/messageException/adminBigMoney";
    }


}
