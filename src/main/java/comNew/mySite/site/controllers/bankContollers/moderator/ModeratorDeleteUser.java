package comNew.mySite.site.controllers.bankContollers.moderator;

import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/bank/moderator/")
public class ModeratorDeleteUser {

    @Autowired
    UserRepository userRepository;

    @GetMapping("deleteUser")
    public String getDeleteUser(@RequestParam ("idUser") Long idUser, Model model){
        Optional<User> user = userRepository.findById(idUser);
        model.addAttribute("user", user.get());
        return "bank/moderator/moderatorDeleteUser";
    }

    @GetMapping("deleteUserIsDeleted")
    public String postDeleteUser(@RequestParam ("idUser") Long idUser, Model model){
        userRepository.delete(userRepository.getById(idUser));
        model.addAttribute("users",userRepository.findAll());
        return "bank/moderator/moderator";
    }
}
