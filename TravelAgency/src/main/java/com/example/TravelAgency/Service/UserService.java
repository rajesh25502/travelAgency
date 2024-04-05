package com.example.TravelAgency.Service;

import com.example.TravelAgency.Config.JWTProvider;
import com.example.TravelAgency.Entity.User;
import com.example.TravelAgency.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTProvider jwtProvider;

    public User findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJWTToken(jwt);
        User user=findUserByEmail(email);
        return user;
    }

    public User findUserByEmail(String email) throws Exception {
        User user=userRepo.findByEmail(email);
        if(user==null)
        {
            throw new Exception("User not found");
        }
        return user;
    }
}

