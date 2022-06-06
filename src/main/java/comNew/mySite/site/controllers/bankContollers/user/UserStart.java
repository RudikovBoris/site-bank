package comNew.mySite.site.controllers.bankContollers.user;

import comNew.mySite.site.controllers.bankContollers.admin.Accounts;
import comNew.mySite.site.jwt.JwtUtils;
import comNew.mySite.site.repository.AccountRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/bank/")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserStart {
    @Autowired
    JwtUtils jwtUtils;
    @Value("${app.headerKey}")
    private String headerKey;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @GetMapping("user1")
    public String accountsUser(HttpServletRequest request, Model model){
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
}
}