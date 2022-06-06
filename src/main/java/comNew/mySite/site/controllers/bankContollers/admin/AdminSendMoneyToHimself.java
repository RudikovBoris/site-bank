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
public class AdminSendMoneyToHimself {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;


    @GetMapping("changeAccountSendToHimself")
    public String getPage(@RequestParam("idAccount") Long id, Model model){
        int numberAccountWeHave = 0;
        Optional<Accounts> accountFromWeWillSend = accountRepository.findById(id);
        numberAccountWeHave=accountFromWeWillSend.get().getNumberAccount();
        List<Accounts> accountsList =accountRepository.findByIdUser(  (accountFromWeWillSend.get().getIdUser()));
        Optional<User> user = userRepository.findById( (accountFromWeWillSend.get().getIdUser()));
        model.addAttribute("userFull", user.get());
        model.addAttribute("accountFull",accountsList);

        model.addAttribute("idAccount", user.get().getId());
        model.addAttribute("money",accountFromWeWillSend.get().getMoney());
        numberAccountWeHave=accountFromWeWillSend.get().getNumberAccount();
        model.addAttribute("id",id);
        model.addAttribute("numberAccountWeHave",numberAccountWeHave);
        return "bank/admin/adminChangeAccountSendToHimself";
    }

    @PostMapping("changeAccountSendToHimself")
    public String postSendMoneyFromCounts(@RequestParam("idAccount") Long idAccount,@RequestParam Long numberAccount, @RequestParam BigDecimal money, Model model){

        int numberAccountWeHave = 0;
        Optional<Accounts> accountFromWeWillSend = accountRepository.findById(idAccount);
        numberAccountWeHave=accountFromWeWillSend.get().getNumberAccount();
        List<Accounts> accountsList =accountRepository.findByIdUser(  (accountFromWeWillSend.get().getIdUser()));
        Optional<User> user = userRepository.findById( (accountFromWeWillSend.get().getIdUser()));
        model.addAttribute("userFull", user.get());



            model.addAttribute("accountFull",accountsList);





        if (numberAccount<=accountsList.size()){
            if (money.compareTo(accountFromWeWillSend.get().getMoney())<=0){

               BigDecimal fromWeSend =  accountFromWeWillSend.get().getMoney().subtract(money);
               BigDecimal forWePut = accountsList.get((numberAccount.intValue())-1).getMoney().add(money);
               accountsList.get(numberAccountWeHave-1).setMoney(fromWeSend);
                accountsList.get(numberAccount.intValue()-1).setMoney(forWePut);
                accountRepository.save(accountsList.get(numberAccountWeHave-1));
                accountRepository.save(accountsList.get(numberAccount.intValue()-1));


                    model.addAttribute("accountFull",accountsList);

                model.addAttribute("idAccount", user.get().getId());
                return "bank/admin/adminUserIsFound";
            }
            model.addAttribute("id",idAccount);
            model.addAttribute("numberAccountWeHave",numberAccountWeHave);
            model.addAttribute("money",accountsList.get(numberAccountWeHave-1).getMoney());
            model.addAttribute("idAccount", user.get().getId());
            return "bank/messageException/adminUserIsFoundNotMoney";
        }
        model.addAttribute("id",idAccount);
        model.addAttribute("numberAccountWeHave",numberAccountWeHave);
        model.addAttribute("money",accountsList.get(numberAccountWeHave-1).getMoney());
        model.addAttribute("idAccount", user.get().getId());
        return "bank/messageException/adminUserIsFoundNotAccount";


    }
}
