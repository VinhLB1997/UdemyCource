package com.spring.blogapp.util;

import com.spring.blogapp.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt-token-scretkey}")
    private String SECRET_KET;

    @Value("${jwt-token-expried}")
    private Long timeExpried;

    // generate JWT token
    public String genarateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date exprieDate = new Date(currentDate.getTime() + timeExpried);
        return Jwts.builder().setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(exprieDate).signWith(genKey())
                .compact();
    }

    private Key genKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KET));
    }

    // Get Infor from payload token
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(genKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // validate Jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(genKey())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}
