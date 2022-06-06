package comNew.mySite.site.controllers.bankContollers;

import comNew.mySite.site.jwt.JwtUtils;
import comNew.mySite.site.repository.UserRepository;
import comNew.mySite.site.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class BankLogin {

    @Autowired
    UserRepository userRepository;

    @Value("${app.jwtBegin}")
    private String jwtBegin;

    @Value("${app.headerKey}")
    private String headerKey;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("bank/login")
    public String getRequestRegistration2(Model model) {
        return "bank/bankLogin";
    }


    @PostMapping("login")
       public ResponseEntity<?> authUser(@RequestParam String username, @RequestParam String password) throws URISyntaxException {

        if(!userRepository.existsByUsername(username)){
            URI bank = new URI("/bank/exceptionUserIsNotFound");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(bank);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }


        if(!userRepository.findByUsername(username).get().getAccountNonLocked()){
            URI bank = new URI("/bank/exceptionUserIsBlock");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(bank);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }




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

    }
}



