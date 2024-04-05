package com.example.TravelAgency.Controller;

import com.example.TravelAgency.Config.JWTProvider;
import com.example.TravelAgency.DTO.AuthResponse;
import com.example.TravelAgency.DTO.LoginRequest;
import com.example.TravelAgency.Entity.USER_ROLE;
import com.example.TravelAgency.Entity.User;
import com.example.TravelAgency.Repository.UserRepo;
import com.example.TravelAgency.Service.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist=userRepo.findByEmail(user.getEmail());
        if(isEmailExist!=null)
        {
            throw new Exception("Email has already registered with account");
        }

        User createdUser=new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setName(user.getName());
        createdUser.setBalance(user.getBalance());
        createdUser.setSubscriptionType(user.getSubscriptionType());
        User savedUser=userRepo.save(createdUser);


        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Successfully Registered .....");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest req)
    {
        String userName= req.getEmail();;
        String password=req.getPassword();
        Authentication authentication=authenticate(userName,password);

        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Successfully SignedIn .....");
        authResponse.setRole(USER_ROLE.valueOf(role));
        authResponse.setUserName(authentication.getName());

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails=customerUserDetailService.loadUserByUsername(userName);

        if(userDetails==null)
        {
            throw new BadCredentialsException("Invalid username.....");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid password......");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}

