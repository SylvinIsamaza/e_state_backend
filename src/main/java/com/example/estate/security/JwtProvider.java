package com.example.estate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {

  public static String generateToken(Authentication auth) { 
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities(); 
		String roles = populateAuthorities(authorities); 
		
		String jwt = Jwts.builder() 
				.setIssuedAt(new Date()) 
				.setExpiration(new Date(new Date().getTime()+86400000)) 
				.claim("email", auth.getName())
				.claim( "authorities",roles) 
				.signWith(SignatureAlgorithm.HS512,JwtConstant.SECRET_KEY) 
				.compact(); 
		System.out.println("Token for parsing in JwtProvider: " + jwt); 
		return jwt; 

	}

  private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Set<String> auths = new HashSet<>();
    for (GrantedAuthority authority : authorities) {
      auths.add(authority.getAuthority());
    }
    return String.join(",", auths);
  }

 
  public static String getEmailFromJwtToken(String jwt) {
    jwt = jwt.substring(7);
    try {
      
      Claims claims = Jwts.parser().setSigningKey(JwtConstant.SECRET_KEY.getBytes()).parseClaimsJws(jwt).getBody();
      String email = String.valueOf(claims.get("email"));
      System.out.println("Email extracted from JWT: " + claims);
      return email;
    } catch (Exception e) {
      System.err.println("Error extracting email from JWT: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

}
