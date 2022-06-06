package comNew.mySite.site.controllers.bankContollers.admin;

import comNew.mySite.site.repository.RoleRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.rolesUsersAndPermissions.ERole;
import comNew.mySite.site.rolesUsersAndPermissions.Role;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static comNew.mySite.site.controllers.bankContollers.admin.AdminStart.userId;

@EnableWebSecurity
@Controller
@RequestMapping("/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminMakeUser {

    String stringRole;


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userInDatabaseRepositoryExtends;
    @Autowired
    RoleRepository userRolesInDatabaseRepositoryExtends;



    private String passwordAfterEncoding;


    @GetMapping("adminRegistration")
    public String getRequestRegistration(Model model) {
        return "bank/admin/adminMakeUser";
    }

    @PostMapping("adminRegistration")
    public String postRequestRegistration(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String role,Model model){

        if(userInDatabaseRepositoryExtends.existsByUsername(username)){
            return "bank/messageException/adminExceptionNameUserExists";
        }

        if(userInDatabaseRepositoryExtends.existsByEmail(email)){
            return "bank/messageException/adminExceptionMailExists";
        }

        passwordAfterEncoding=passwordEncoder.encode(password);


        User user = new User( username,email,passwordAfterEncoding);

        Set<String> requestParamRole = new HashSet<>();
        requestParamRole.add(role);
        Set<Role> rolesInDatabases = new HashSet<>();



        requestParamRole.forEach(forEachRole->{
            switch (forEachRole){
                case "user":
                    Role userRolesInDatabase = userRolesInDatabaseRepositoryExtends
                            .findByUserRoles(ERole.ROLE_USER)
                            .orElseThrow(()-> new RuntimeException("!!!Role User in case not found from RegistrationController!!!"));
                    user.setRoleString("User");
                    rolesInDatabases.add(userRolesInDatabase);
                    break;
                case "admin":
                    Role adminRolesInDatabase = userRolesInDatabaseRepositoryExtends
                            .findByUserRoles(ERole.ROLE_ADMIN)
                            .orElseThrow(()-> new RuntimeException("!!!Role Admin in case not found from RegistrationController!!!"));
                    user.setRoleString("Admin");
                    rolesInDatabases.add(adminRolesInDatabase);
                    break;
                case "moderator":
                    Role moderatorRolesInDatabase = userRolesInDatabaseRepositoryExtends
                            .findByUserRoles(ERole.ROLE_MODERATOR)
                            .orElseThrow(()-> new RuntimeException("!!!Role Moderator in case not found from RegistrationController!!!"));
                    user.setRoleString("Moderator");
                    rolesInDatabases.add(moderatorRolesInDatabase);
                    break;
            }

        });

        user.setRoles(rolesInDatabases);
        userInDatabaseRepositoryExtends.save(user);
        model.addAttribute("userFull", user);
        model.addAttribute("userId", user.getId());
        userId = user.getId();;

        return "bank/admin/adminUserIsFound";
    }


}
