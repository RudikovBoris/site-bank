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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bank/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminSendMoneyToAnotherAccount {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @GetMapping("AnotherAccountSend")
    public String getSendMoneyToAnotherAccount(@RequestParam("idAccount") Long idAccount, Model model){
        int numberAccountWeHave = 0;
        Optional<Accounts> accountFromWeWillSend = accountRepository.findById(idAccount);
        numberAccountWeHave=accountFromWeWillSend.get().getNumberAccount();
        List<Accounts> accountsList =accountRepository.findByIdUser(  (accountFromWeWillSend.get().getIdUser()));
        Optional<User> user = userRepository.findById( (accountFromWeWillSend.get().getIdUser()));
        model.addAttribute("userFull", user.get());
            model.addAttribute("accountFull",accountsList);

        model.addAttribute("idAccount", user.get().getId());
        model.addAttribute("money",accountFromWeWillSend.get().getMoney());
        numberAccountWeHave=accountFromWeWillSend.get().getNumberAccount();
        model.addAttribute("id",idAccount);
        model.addAttribute("numberAccountWeHave",numberAccountWeHave);
        return "bank/admin/adminChangeSendToAnotherAccount";
    }

    @PostMapping("AnotherAccountSend")
    public String postSendMoneyToAnotherAccount(@RequestParam("idAccount") Long idAccount,
                                                @RequestParam String nameUserWeWillSend,
                                                @RequestParam Long idAccountWeWillSend,
                                                @RequestParam BigDecimal money,
                                                Model model) {




     //   model.addAttribute("numberAccountWeHave",idAccount);
        Optional<Accounts> oursAccountsOptional = accountRepository.findById(idAccount);
        Optional<User> user = userRepository.findById(oursAccountsOptional.get().idUser);
        List<Accounts> oursAccountList = accountRepository.findByIdUser(oursAccountsOptional.get().idUser);
        BigDecimal moneyAssAccount =(oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getMoney());
        if (userRepository.existsByUsername(nameUserWeWillSend)) {
            Optional<User> userWeWillSend = userRepository.findByUsername(nameUserWeWillSend);




                List<Accounts> accountsWeWillSend = accountRepository.findByIdUser(userWeWillSend.get().getId());

                moneyAssAccount=moneyAssAccount.subtract(money);

                if (accountsWeWillSend.size()>=idAccountWeWillSend) {
                if(oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getMoney().compareTo(money)>=0) {
                    BigDecimal moneyAnotherAccount = (accountsWeWillSend.get(idAccountWeWillSend.intValue() - 1).getMoney()).add(money);

                    oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).setMoney(moneyAssAccount);
                    accountsWeWillSend.get(idAccountWeWillSend.intValue() - 1).setMoney(moneyAnotherAccount);

                    accountRepository.save(oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1));
                    accountRepository.save(accountsWeWillSend.get(idAccountWeWillSend.intValue() - 1));

                    oursAccountList = accountRepository.findByIdUser(oursAccountsOptional.get().idUser);
                    model.addAttribute("userFull", user.get());

                        model.addAttribute("accountFull", oursAccountList);

                    model.addAttribute("idAccount", user.get().getId());
                    return "bank/admin/adminUserIsFound";
                }
                    model.addAttribute("numberAccountWeHave",oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getNumberAccount());
                    model.addAttribute("money",oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getMoney());
                    model.addAttribute("id",idAccount);
                    model.addAttribute("idAccount", user.get().getId());
                 //   return "bank/admin/adminUserIsFound"; //мало денег
                    return "bank/messageException/adminChangeSendToAnotherAccountExceptionNotMoney";
            }
            model.addAttribute("numberAccountWeHave",oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getNumberAccount());
            model.addAttribute("money",oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getMoney());
            model.addAttribute("id",idAccount);
            model.addAttribute("idAccount", user.get().getId());
         //   return "bank/admin/adminUserIsFound"; //номера такого аккаунта
            return "bank/messageException/adminChangeSendToAnotherAccountExceptionNotAccount";
        }
        model.addAttribute("numberAccountWeHave",oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getNumberAccount());
        model.addAttribute("money",oursAccountList.get(oursAccountsOptional.get().getNumberAccount() - 1).getMoney());
        model.addAttribute("id",idAccount);
        model.addAttribute("idAccount", user.get().getId());
       // return "bank/admin/adminUserIsFound"; //имя не
        return "bank/messageException/adminChangeSendToAnotherAccountExceptionNotUser";
    }
}
