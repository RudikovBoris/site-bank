package comNew.mySite.site.controllers.bankContollers.admin;

import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.RoleRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@Controller
@RequestMapping("/bank/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminStart {
    @Autowired
    private RoleRepository roleRepository;
    List<Accounts> accounts;
    @Autowired
    AccountRepository accountRepository;
    String stringRole;

    public static Long userId= null;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("admin")
    public String userGetter(@RequestParam(value = "idAccount", required = false) Long idAccount,@RequestParam(value = "idUser", required = false) Long idUser, Model model) {
        if(idAccount==null){
            System.out.println("null");
        }else{


            Optional<User> user = userRepository.findById(idAccount);
            stringRole = user.get().getRoleString();

            if (stringRole.equals("User")) {
                model.addAttribute("userFull", user.get());
                userId= user.get().getId();


                if (accountRepository.existsByIdUser(userId)) {
                    accounts = accountRepository.findByIdUser(userId);



                        model.addAttribute("accountFull",accounts);

                }
                return "bank/admin/adminUserIsFound";
            }
        }

        return "bank/admin/admin";
    }

    @PostMapping("admin")
    public String tableOfUserFoundUserByUsername(@RequestParam String usernameSearch, Model model) {



        if (usernameSearch != "") {
            if(userRepository.existsByUsername(usernameSearch)){
                 Optional<User> user = userRepository.findByUsername(usernameSearch);
                 stringRole = user.get().getRoleString();

                if (stringRole.equals("User")) {
                    model.addAttribute("userFull", user.get());
                     userId= user.get().getId();


                    if (accountRepository.existsByIdUser(userId)) {
                        accounts = accountRepository.findByIdUser(userId);


                            model.addAttribute("accountFull",accounts);

                    }



                    return "bank/admin/adminUserIsFound";
                } else {
                    return "bank/messageException/adminUserIsFoundException";
                }
            }else {
            model.addAttribute("userNotInTheDatabase", usernameSearch);
            return   "bank/messageException/adminUserFoundExceptionUserNotInDataBase";
        }}
        return "bank/admin/admin";

    }
}