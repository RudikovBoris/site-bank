package comNew.mySite.site.controllers.bankContollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank/")
public class BankRegistrationException {


    @GetMapping("exceptionUser")
    public String adminRegistrationExceptionUserExist(){
            return "bank/messageException/messageResponseUserExist";
    }

    @GetMapping("exceptionMail")
    public String adminRegistrationExceptionMailExist(){
        return "bank/messageException/messageResponseMailExist";
    }

    @GetMapping("exceptionUserIsBlock")
    public String adminRegistrationExceptionUserIsBlock(){
        return "bank/messageException/bankExceptionUserOreAdminIsBlock";
    }

    @GetMapping("exceptionUserIsNotFound")
    public String loginUserIsNotFound (){
        return "bank/messageException/loginExceptionUserNotFound";

    }

    @GetMapping("exceptionPasswordIsNotCorrect")
    public String loginPasswordIsNotCorrect(){
        return "bank/messageException/401_loginPasswordIsNotCorrect";

    }
}
