package com.jucalc.spring_login.services.Impl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jucalc.spring_login.entities.User;
import com.jucalc.spring_login.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;



@Service
public class JwtServiceImpl implements JwtService {

  @Value("${token.signig.key}")
  private String jwtSignigKey;

  @Value("${token.expiration}")
  private Long jwtExpiration;
  

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public String generateToken(User user) {
    Map<String, Object> extraClaims = Map.of("userid", user.getId());
    return generateToken(extraClaims, user);
  }

  @Override
  public boolean isTokenValid(String token, UserDetails user) {
    final String userName = extractUsername(token);
    if(isTokenExpired(token)){
      throw new ExpiredJwtException(null, extractAllClaims(token), "Token expirado");
    }
    return (userName.equals(user.getUsername())) && !isTokenExpired(token);
  }
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, UserDetails user) {
    return Jwts.builder().claims()
      .add(extraClaims)
      .subject(user.getUsername())
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(jwtExpiration)))
      .and()
      .signWith(getKey())
      .compact();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  private Key getKey() {
    byte[] decodedKey = Base64.getDecoder().decode(jwtSignigKey);
    return Keys.hmacShaKeyFor(decodedKey);
  }

  @Override
  public SecretKey getSigningKey() {
    byte[] decodedKey = Base64.getDecoder().decode(jwtSignigKey);
    SecretKey key = Keys.hmacShaKeyFor(decodedKey);
    return key;
  }
  
}
