package com.example.TravelAgency.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JWTProvider {
    private SecretKey key= Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth)
    {
        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        String roles=populateAuthoriries(authorities);

        String jwt= Jwts.builder().setIssuedAt(new Date())
                .setExpiration((new Date(new Date().getTime()+86400000)))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();

        return  jwt;
    }

    public String getEmailFromJWTToken(String jwt)
    {
        jwt=jwt.substring(7);
        //jwt = jwt.trim().toString();
        Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email=String.valueOf(claims.get("email"));
       return email;
    }

    private String populateAuthoriries(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths=new HashSet<>();
        for(GrantedAuthority authority:authorities)
        {
            auths.add(authority.getAuthority());
        }

        return String.join(",",auths);
    }
}
