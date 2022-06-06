package comNew.mySite.site.controllers.bankContollers;

import comNew.mySite.site.controllers.bankContollers.admin.Accounts;
import comNew.mySite.site.jwt.JwtUtils;
import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.RoleRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import comNew.mySite.site.service.UserDetailsImpl;
import comNew.mySite.site.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class Bank_Controller {
    @Autowired
    JwtUtils jwtUtils;
    @Value("${app.headerKey}")
    private String headerKey;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @GetMapping ("/bank")
    public String bankMain (HttpServletRequest request, Model model){
        if(request.isUserInRole("ROLE_USER")){
             Cookie[] cookies = request.getCookies();
            Map<String,String> mupCookie = new HashMap<>();
            String jwt = "null";
            for (int i = 0; i < cookies.length; i++) {
                if (headerKey.equals(cookies[i].getName())){
                    mupCookie.put(cookies[i].getName(),cookies[i].getValue());
                    jwt = cookies[i].getValue();
                }
            }
            String name = jwtUtils.getUserNameFromJwtToken(jwt);
            Optional<User> user = userRepository.findByUsername(name);
            List<Accounts> accounts = accountRepository.findByIdUser(user.get().getId());

            model.addAttribute("accounts",accounts);
            model.addAttribute("user", user.get());
            return "bank/user/user";
        }else if (request.isUserInRole("ROLE_ADMIN")){
            return "bank/admin/admin";
        }else if(request.isUserInRole("ROLE_MODERATOR")) {
            List<User> userList = userRepository.findAll();
            model.addAttribute("users",userList);
            return "bank/moderator/moderator";
        }
        return "bank/bank";

    }


}
