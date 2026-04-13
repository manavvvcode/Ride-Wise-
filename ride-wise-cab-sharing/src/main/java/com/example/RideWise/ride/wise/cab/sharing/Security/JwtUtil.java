package com.example.RideWise.ride.wise.cab.sharing.Security;


import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private  String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject("riderToken")
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date(System.currentTimeMillis()+8*60*60*1000))
                .claim("roles","ROLE_"+user.getRole())
                .signWith(getSecretKey())
                .compact();
    }
}
