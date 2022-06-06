package comNew.mySite.site.controllers.bankContollers.moderator;

import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bank/")
@PreAuthorize("hasRole('ROLE_MODERATOR')")
public class ModeratorStart {
    @Autowired
    UserRepository userRepository;

    @GetMapping("moderator")
    public String userAccounts(Model model){
        List<User> userList = userRepository.findAll();
        model.addAttribute("users",userList);
        return "bank/moderator/moderator";
    }

}
