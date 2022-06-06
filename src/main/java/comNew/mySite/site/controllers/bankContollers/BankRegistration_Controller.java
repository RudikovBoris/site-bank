package comNew.mySite.site.controllers.bankContollers;

import comNew.mySite.site.jwt.JwtUtils;
import comNew.mySite.site.rolesUsersAndPermissions.ERole;
import comNew.mySite.site.rolesUsersAndPermissions.Role;
import comNew.mySite.site.rolesUsersAndPermissions.User;
import comNew.mySite.site.repository.RoleRepository;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EnableWebSecurity
@Controller
@RequestMapping("/")
public class BankRegistration_Controller{


    @Value("${app.jwtBegin}")
    private String jwtBegin;

    @Value("${app.headerKey}")
    private String headerKey;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userInDatabaseRepositoryExtends;
    @Autowired
    RoleRepository userRolesInDatabaseRepositoryExtends;

    private String passwordAfterEncoding;


    @GetMapping("bank/bankRegistration")
    public String getRequestRegistration(Model model) {
        return "bank/bankRegistration";
    }



    @PostMapping("registration")
    public ResponseEntity<?> postRequestRegistration(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String role, Model model) throws URISyntaxException {

        if(userInDatabaseRepositoryExtends.existsByUsername(username)){
        URI yahoo = new URI("/bank/exceptionUser");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(yahoo);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        if(userInDatabaseRepositoryExtends.existsByEmail(email)){
        URI yahoo = new URI("/bank/exceptionMail");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(yahoo);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
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




        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        username,
                        password));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        URI yahoo = new URI("/bank");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", headerKey+"="+ jwt);
        headers.setLocation(yahoo);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);




//        if(user.getRoleString().equals("Admin")){
//            return "bank/admin/admin";
//        }
//        List<User> users = userInDatabaseRepositoryExtends.findAll();
//        model.addAttribute("users", users );
//        return "bank/moderator/moderator";
    }
}
