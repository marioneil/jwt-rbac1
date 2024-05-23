package com.weather.rbac1.util;

import com.weather.rbac1.dto.AppUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtility {

    private String jwtSecret = "ao8xk88dh9nb5yao03wg1dnj1zdr9broel8jqp8447etjy5zjjaq2nyfj2el1v5f";
    private int jwtExpirationMs = 60000;



    public String generateToken(Authentication authentication){
        AppUserDetails userPrincipal = (AppUserDetails) authentication.getPrincipal();

        String token = Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256).compact();

        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String token){
        try{
        Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
        return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


    public String getUserName(String jwtToken){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwtToken).getBody().getSubject();
    }

}
